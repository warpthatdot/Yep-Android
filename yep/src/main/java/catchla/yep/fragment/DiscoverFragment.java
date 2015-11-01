/*
 * Copyright (c) 2015. Catch Inc,
 */

package catchla.yep.fragment;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import java.util.List;

import catchla.yep.R;
import catchla.yep.activity.UserActivity;
import catchla.yep.adapter.UsersAdapter;
import catchla.yep.adapter.decorator.DividerItemDecoration;
import catchla.yep.adapter.iface.ItemClickListener;
import catchla.yep.loader.DiscoverLoader;
import catchla.yep.model.DiscoverQuery;
import catchla.yep.model.TaskResponse;
import catchla.yep.model.User;

/**
 * Created by mariotaku on 15/4/29.
 */
public class DiscoverFragment extends AbsContentListRecyclerViewFragment<UsersAdapter>
        implements LoaderManager.LoaderCallbacks<TaskResponse<List<User>>>, ItemClickListener {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        final Context viewContext = getActivity();

        final RecyclerView recyclerView = getRecyclerView();
        final LinearLayoutManager layoutManager = getLayoutManager();
        final DividerItemDecoration itemDecoration = new DividerItemDecoration(viewContext, layoutManager.getOrientation());
        final Resources res = viewContext.getResources();
        final int decorPaddingLeft = res.getDimensionPixelSize(R.dimen.element_spacing_normal) * 2
                + res.getDimensionPixelSize(R.dimen.icon_size_status_profile_image);
        itemDecoration.setPadding(decorPaddingLeft, 0, 0, 0);
        recyclerView.addItemDecoration(itemDecoration);
        final Bundle fragmentArgs = getArguments();
        final Bundle loaderArgs = new Bundle();
        if (fragmentArgs != null) {
            loaderArgs.putBoolean(EXTRA_READ_CACHE, !fragmentArgs.containsKey(EXTRA_LEARNING)
                    && !fragmentArgs.containsKey(EXTRA_MASTER));
        } else {
            loaderArgs.putBoolean(EXTRA_READ_CACHE, true);
        }
        getLoaderManager().initLoader(0, loaderArgs, this);
        getAdapter().setClickListener(this);
        showProgress();
    }


    @Override
    public Loader<TaskResponse<List<User>>> onCreateLoader(final int id, final Bundle args) {
        final DiscoverQuery query = new DiscoverQuery();
        final Bundle fragmentArgs = getArguments();
        final boolean readCache = args.getBoolean(EXTRA_READ_CACHE);
        boolean writeCache = true;
        if (fragmentArgs != null) {
            if (fragmentArgs.containsKey(EXTRA_LEARNING)) {
                query.learningSkills(fragmentArgs.getStringArray(EXTRA_LEARNING));
                writeCache = false;
            }
            if (fragmentArgs.containsKey(EXTRA_MASTER)) {
                query.masterSkills(fragmentArgs.getStringArray(EXTRA_MASTER));
                writeCache = false;
            }
        }
        return new DiscoverLoader(getActivity(), getAccount(), query, readCache, writeCache);
    }

    @Override
    public void onLoadFinished(final Loader<TaskResponse<List<User>>> loader, final TaskResponse<List<User>> data) {
        getAdapter().setData(data.getData());
        showContent();
        setRefreshing(false);
    }

    @Override
    public void onLoaderReset(final Loader<TaskResponse<List<User>>> loader) {

    }

    @NonNull
    @Override
    protected UsersAdapter onCreateAdapter(Context context) {
        return new UsersAdapter(context);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_chats_list, menu);
    }

    @Override
    public void onBaseViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onBaseViewCreated(view, savedInstanceState);
    }

    @Override
    public void onRefresh() {
        final Bundle loaderArgs = new Bundle();
        loaderArgs.putBoolean(EXTRA_READ_CACHE, false);
        getLoaderManager().restartLoader(0, loaderArgs, this);
    }

    @Override
    public boolean isRefreshing() {
        return getLoaderManager().hasRunningLoaders();
    }


    @Override
    public void onItemClick(final int position, final RecyclerView.ViewHolder holder) {
        final User user = getAdapter().getUser(position);
        final Intent intent = new Intent(getActivity(), UserActivity.class);
        intent.putExtra(EXTRA_ACCOUNT, getAccount());
        intent.putExtra(EXTRA_USER, user);
        startActivity(intent);
    }

    private Account getAccount() {
        return getArguments().getParcelable(EXTRA_ACCOUNT);
    }
}
