package com.tangtao.nineimglayoutlib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tangtao.nineimglayoutlib.R;

public class NineImgLayout extends RecyclerView {

    private Context mContext;
    private GridLayoutManager mGridLayoutManager;
    private int mMaxSelectCount = 9;
    private int mSpanCount = 5;


    public NineImgLayout(@NonNull Context context) {
        this(context, null);
    }

    public NineImgLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineImgLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.NineImgLayout);
        mSpanCount = array.getInteger(R.styleable.NineImgLayout_span_count, 5);
        mMaxSelectCount = array.getInteger(R.styleable.NineImgLayout_max_select_count, 9);
        array.recycle();
    }


    public void setGridManager(Context context) {
        setGridManager(context, mSpanCount);
    }

    public void setGridManager(Context context, int spanCount) {
        mContext = context;
        mSpanCount = spanCount;
        if (mGridLayoutManager == null) {
            mGridLayoutManager = new GridLayoutManager(mContext, mSpanCount);
            NineImgLayout.this.setLayoutManager(mGridLayoutManager);
        }
    }

}
