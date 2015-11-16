package catchla.yep.service;

import android.accounts.Account;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.otto.Bus;

import org.json.JSONException;
import org.json.JSONObject;
import org.mariotaku.sqliteqb.library.Expression;

import java.io.IOException;
import java.util.Collections;
import java.util.Locale;

import javax.inject.Inject;

import catchla.yep.Constants;
import catchla.yep.model.InstantStateMessage;
import catchla.yep.model.MarkAsReadMessage;
import catchla.yep.model.Message;
import catchla.yep.provider.YepDataStore.Messages;
import catchla.yep.util.FayeClient;
import catchla.yep.util.JsonSerializer;
import catchla.yep.util.Utils;
import catchla.yep.util.YepAPIFactory;
import catchla.yep.util.dagger.ApplicationModule;
import catchla.yep.util.dagger.DaggerGeneralComponent;

public class FayeService extends Service implements Constants {

    @Inject
    Bus mBus;
    private FayeClient mFayeClient;
    private Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler(Looper.getMainLooper());
        DaggerGeneralComponent.builder().applicationModule(ApplicationModule.get(this)).build().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Account account = intent.getParcelableExtra(EXTRA_ACCOUNT);
        final OkHttpClient client = YepAPIFactory.getOkHttpClient(this);
        final Request.Builder builder = new Request.Builder();
        builder.url(YepAPIFactory.API_ENDPOINT_FAYE);
        final String authToken = YepAPIFactory.getAuthToken(this, account);
        final String accountId = Utils.getAccountId(this, account);

        mFayeClient = FayeClient.create(client, builder.build());
        mFayeClient.addExtension(new FayeClient.FayeExtension() {

            @Override
            public void processIncoming(final FayeClient.Message message) {
            }

            @Override
            public void processOutgoing(final FayeClient.Message message) {
                JSONObject ext = new JSONObject();
                try {
                    ext.put("version", "v1");
                    ext.put("access_token", authToken);
                    message.put("ext", ext);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        final String userChannel = String.format(Locale.ROOT, "/v1/users/%s/messages", accountId);
        mFayeClient.establish(new FayeClient.ConnectionListener() {
            @Override
            public void onConnected() {
                Log.d(LOGTAG, "Connected");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (mFayeClient.isConnected()) {
                            mFayeClient.ping();
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }

            @Override
            public void onFailure(final IOException e) {
                Log.w(LOGTAG, e);
            }

            @Override
            public void onClose(final int code, final String reason) {
                Log.d(LOGTAG, "Closed " + code);
            }
        }).handshake(new FayeClient.Callback() {
            @Override
            public void callback(final FayeClient.Message message) {
            }
        }).subscribe(userChannel, new FayeClient.Callback() {
            @Override
            public void callback(final FayeClient.Message message) {
                final JSONObject data = message.getJSONObject("data");
                final String msgType = data.optString("message_type");
                if ("message".equals(msgType)) {
                    Message imMessage = JsonSerializer.parse(data.optJSONObject("message").toString(),
                            Message.class);
                    MessageService.insertMessages(FayeService.this, Collections.singleton(imMessage), accountId);
                } else if ("mark_as_read".equals(msgType)) {
                    MarkAsReadMessage markAsRead = JsonSerializer.parse(data.optJSONObject("message").toString(),
                            MarkAsReadMessage.class);
                    if (markAsRead != null) {
                        final ContentValues values = new ContentValues();
                        values.put(Messages.STATE, Messages.MessageState.READ);
                        final Expression where = Expression.and(
                                Expression.equalsArgs(Messages.RECIPIENT_ID),
                                Expression.equalsArgs(Messages.RECIPIENT_TYPE),
                                Expression.lesserEquals(Messages.CREATED_AT, markAsRead.getLastReadAt().getTime())
                        );
                        final String[] whereArgs = {markAsRead.getRecipientId(), markAsRead.getRecipientType()};
                        getContentResolver().update(Messages.CONTENT_URI, values, where.getSQL(), whereArgs);
                    }
                } else if ("instant_state".equals(msgType)) {
                    InstantStateMessage instantState = JsonSerializer.parse(data.optJSONObject("message").toString(),
                            InstantStateMessage.class);
                    if (instantState != null) {
                        postMessage(instantState);
                    }
                }
            }
        });
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mFayeClient != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mFayeClient.disconnect();
                }
            }).start();
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(final Intent intent) {
        throw new UnsupportedOperationException();
    }

    private void postMessage(final Object obj) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mBus.post(obj);
            }
        });
    }

}