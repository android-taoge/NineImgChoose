package com.tangtao.nineimglayoutlib.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tangtao.nineimglayoutlib.R;

import java.util.Collection;
import java.util.List;

public abstract class BaseNineImgAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private List<T> mData;
    public Context mContext;
    private int maxSelectCount = 9;
    private int mLayoutResId;
    private int mEmptyResId;
    private static final int CONTENT_TYPE = 1;
    private static final int EMPTY_TYPE = 2;
    private OnItemClickListener mOnItemClickListener;
    private OnItemChildClickListener mOnItemChildClickListener;

    public BaseNineImgAdapter(Context mContext, List<T> mData, int mLayoutResId, int emptyResId, int maxSelectCount) {
        this.mData = mData;
        this.mContext = mContext;
        this.maxSelectCount = maxSelectCount;
        this.mLayoutResId = mLayoutResId;
        this.mEmptyResId = emptyResId;
    }


    @Override
    public int getItemViewType(int position) {
        if (mData.size() == 0) {
            return EMPTY_TYPE;
        } else if (position < mData.size()) {
            return CONTENT_TYPE;
        } else {
            return EMPTY_TYPE;
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseViewHolder holder = null;
        View view = null;
        switch (viewType) {
            case CONTENT_TYPE:
                view = LayoutInflater.from(mContext).inflate(mLayoutResId, parent, false);
                break;

            case EMPTY_TYPE:
                view = LayoutInflater.from(mContext).inflate(mEmptyResId, parent, false);
                break;

        }
        holder = new BaseViewHolder(view);
        bindViewClickListener(holder);
        holder.setAdapter(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.addOnClickListener(R.id.iv_close);
        convert(holder, getItem(position));
    }

    protected abstract void convert(BaseViewHolder holder, T item);

    private T getItem(@IntRange(from = 0) int position) {
        if (position < mData.size()) {
            return mData.get(position);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mData.size() < maxSelectCount ? mData.size() + 1 : mData.size();
    }


    public void addData(@IntRange(from = 0) int position, @NonNull T data) {
        mData.add(position, data);
        notifyItemInserted(position);

    }

    public void addData(@NonNull Collection<? extends T> newData) {
        mData.addAll(newData);
        notifyItemRangeInserted(mData.size() - newData.size(), newData.size());

    }

    public List<T> getData() {
        return mData;
    }

    public void remove(@IntRange(from = 0) int position) {
        mData.remove(position);
        int internalPosition = position;
        notifyItemRemoved(internalPosition);
        notifyItemRangeChanged(internalPosition, mData.size() - internalPosition);
    }

    private void bindViewClickListener(final BaseViewHolder baseViewHolder) {
        if (baseViewHolder == null) {
            return;
        }
        final View view = baseViewHolder.itemView;
        if (view == null) {
            return;
        }
        if (getOnItemClickListener() != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOnItemClick(v, baseViewHolder.getLayoutPosition());
                }
            });
        }

    }

    private void setOnItemClick(View v, int layoutPosition) {
        getOnItemClickListener().onItemClick(BaseNineImgAdapter.this, v, layoutPosition);
    }


    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemChildClickListener(OnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public OnItemChildClickListener getOnItemChildClickListener() {
        return mOnItemChildClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(BaseNineImgAdapter adapter, View view, int position);
    }

    public interface OnItemChildClickListener {
        void onItemChildClick(BaseNineImgAdapter adapter, View view, int position);
    }
}
