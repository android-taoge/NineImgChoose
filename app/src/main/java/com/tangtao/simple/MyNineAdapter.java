package com.tangtao.simple;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;


import com.tangtao.nineimgchooselayout.R;
import com.tangtao.nineimglayoutlib.widget.BaseNineImgAdapter;
import com.tangtao.nineimglayoutlib.widget.BaseViewHolder;

import java.util.List;

public class MyNineAdapter extends BaseNineImgAdapter<String> {

    public MyNineAdapter(Context mContext, List<String> mData, int layoutResId, int emptyResId, int maxSelectCount) {
        super(mContext, mData, layoutResId, emptyResId, maxSelectCount);
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        ImageView imageView = holder.getView(R.id.iv_img);

        if (imageView != null) {
            ImageUtils.showImage(mContext, imageView, item, R.drawable.ic_add, 5);
            //Log.e("imageView", "==" + imageView.hashCode());
        }
    }
}
