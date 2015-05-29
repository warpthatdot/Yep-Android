package catchla.yep.loader;

import android.accounts.Account;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import catchla.yep.Constants;
import catchla.yep.model.DiscoverQuery;
import catchla.yep.model.Friendship;
import catchla.yep.model.PagedFriendships;
import catchla.yep.model.PagedUsers;
import catchla.yep.model.TaskResponse;
import catchla.yep.model.User;
import catchla.yep.util.YepAPI;
import catchla.yep.util.YepAPIFactory;
import catchla.yep.util.YepException;

/**
 * Created by mariotaku on 15/5/27.
 */
public class DiscoverLoader extends AsyncTaskLoader<TaskResponse<List<User>>> implements Constants {

    private final Account mAccount;
    private final DiscoverQuery mQuery;

    public DiscoverLoader(Context context, Account account, DiscoverQuery query) {
        super(context);
        mAccount = account;
        mQuery = query;
    }

    @Override
    public TaskResponse<List<User>> loadInBackground() {
        final YepAPI yep = YepAPIFactory.getInstance(getContext(), mAccount);
        try {
            final PagedUsers friendships = yep.getDiscover(mQuery);
            final List<User> list = new ArrayList<>();
            list.addAll(friendships);
            return TaskResponse.getInstance(list);
        } catch (YepException e) {
            return TaskResponse.getInstance(e);
        }
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
