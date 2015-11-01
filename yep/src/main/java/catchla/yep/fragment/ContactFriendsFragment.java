package catchla.yep.fragment;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.List;

import catchla.yep.adapter.UsersAdapter;
import catchla.yep.loader.ContactFriendsLoader;
import catchla.yep.model.TaskResponse;
import catchla.yep.model.User;

/**
 * Created by mariotaku on 15/8/25.
 */
public class ContactFriendsFragment extends AbsContentListRecyclerViewFragment<UsersAdapter>
        implements LoaderManager.LoaderCallbacks<TaskResponse<List<User>>> {

    @Override
    public void onRefresh() {
        super.onRefresh();
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean isRefreshing() {
        return false;
    }

    @NonNull
    @Override
    protected UsersAdapter onCreateAdapter(Context context) {
        return new UsersAdapter(context);
    }

    @Override
    public Loader<TaskResponse<List<User>>> onCreateLoader(final int id, final Bundle args) {
        return new ContactFriendsLoader(getActivity(), getAccount());
    }

    private Account getAccount() {
        return getArguments().getParcelable(EXTRA_ACCOUNT);
    }

    @Override
    public void onLoadFinished(final Loader<TaskResponse<List<User>>> loader, final TaskResponse<List<User>> data) {
        getAdapter().setData(data.getData());
        showContent();
        setRefreshing(false);
    }

    @Override
    public void onLoaderReset(final Loader<TaskResponse<List<User>>> loader) {
        getAdapter().setData(null);
    }
}
