/*
 * Copyright (c) 2015. Catch Inc,
 */

package catchla.yep.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import catchla.yep.R;
import catchla.yep.adapter.iface.ItemClickListener;
import catchla.yep.model.Conversation;
import catchla.yep.util.ImageLoaderWrapper;
import catchla.yep.view.holder.ChatEntryViewHolder;

/**
 * Created by mariotaku on 15/4/29.
 */
public class ChatsListAdapter extends LoadMoreSupportAdapter {
    private static final int ITEM_VIEW_TYPE_CHAT_ENTRY = 1;
    private final LayoutInflater mInflater;
    private List<Conversation> mData;
    private ItemClickListener mItemClickListener;

    public ChatsListAdapter(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
    }

    public void setItemClickListener(final ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        final View view = mInflater.inflate(R.layout.list_item_chat_entry, parent, false);
        return new ChatEntryViewHolder(view, this, mItemClickListener);
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_VIEW_TYPE_CHAT_ENTRY;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_VIEW_TYPE_CHAT_ENTRY: {
                ChatEntryViewHolder chatEntryViewHolder = (ChatEntryViewHolder) holder;
                chatEntryViewHolder.displayConversation(mData.get(position));
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null) return 0;
        return mData.size();
    }

    public void setData(final List<Conversation> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public Conversation getConversation(final int position) {
        return mData.get(position);
    }

    public ImageLoaderWrapper getImageLoader() {
        return mImageLoader;
    }
}
