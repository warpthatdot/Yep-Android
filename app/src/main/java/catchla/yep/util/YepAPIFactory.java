package catchla.yep.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Pair;

import com.bluelinelabs.logansquare.LoganSquare;
import com.squareup.okhttp.OkHttpClient;

import org.mariotaku.restfu.ExceptionFactory;
import org.mariotaku.restfu.RequestInfoFactory;
import org.mariotaku.restfu.RestAPIFactory;
import org.mariotaku.restfu.RestMethodInfo;
import org.mariotaku.restfu.RestRequestInfo;
import org.mariotaku.restfu.annotation.RestMethod;
import org.mariotaku.restfu.http.Endpoint;
import org.mariotaku.restfu.http.FileValue;
import org.mariotaku.restfu.http.RestHttpRequest;
import org.mariotaku.restfu.http.RestHttpResponse;
import org.mariotaku.restfu.http.mime.TypedData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import catchla.yep.Constants;
import catchla.yep.util.net.OkHttpRestClient;

/**
 * Created by mariotaku on 15/5/23.
 */
public class YepAPIFactory implements Constants {

    public static YepAPI getInstance(Context context, Account account) {
        if (account == null) return null;
        final AccountManager am = AccountManager.get(context);
        return getInstance(am.peekAuthToken(account, AUTH_TOKEN_TYPE));
    }

    public static YepAPI getInstance(final String accessToken) {
        RestAPIFactory factory = new RestAPIFactory();
        factory.setEndpoint(new Endpoint("http://park-staging.catchchatchina.com/api/"));
        final OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(10, TimeUnit.SECONDS);
        client.setReadTimeout(10, TimeUnit.SECONDS);
        factory.setClient(new OkHttpRestClient(client));
        factory.setConverter(new LoganSquareConverter());
        factory.setRequestInfoFactory(new RequestInfoFactory() {
            @Override
            public RestRequestInfo create(final RestMethodInfo methodInfo) {

                final RestMethod method = methodInfo.getMethod();
                final String path = methodInfo.getPath();
                final List<Pair<String, String>> queries = new ArrayList<>(methodInfo.getQueries());
                final List<Pair<String, String>> forms = new ArrayList<>(methodInfo.getForms());
                final List<Pair<String, String>> headers = methodInfo.getHeaders();
                final List<Pair<String, TypedData>> parts = methodInfo.getParts();
                final FileValue file = methodInfo.getFile();
                final Map<String, Object> extras = methodInfo.getExtras();

                headers.add(Pair.create("Accept", "application/json"));
                if (accessToken != null)
                    headers.add(Pair.create("Authorization",
                            String.format(Locale.ROOT, "Token token=\"%s\"", accessToken)));

                return new RestRequestInfo(method.value(), path, queries, forms, headers, parts, file,
                        methodInfo.getBody(), extras);
            }
        });
        factory.setExceptionFactory(new ExceptionFactory() {
            @Override
            public Exception newException(final Throwable cause, final RestHttpRequest request, final RestHttpResponse response) {
                YepException exception;
                try {
                    if (response != null) {
                        exception = LoganSquare.parse(response.getBody().stream(), YepException.class);
                    } else {
                        exception = new YepException(cause);
                    }
                } catch (IOException e) {
                    exception = new YepException(cause);
                }
                exception.setRequest(request);
                exception.setResponse(response);
                return exception;
            }
        });
        return factory.build(YepAPI.class);
    }

}