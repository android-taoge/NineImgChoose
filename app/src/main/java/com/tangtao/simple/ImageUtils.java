package com.tangtao.simple;

import android.content.Context;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class ImageUtils {

    public static void showImage(Context context, ImageView imageView, String imgUrl, int errorId, int radius) {

        Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions
                        .errorOf(errorId)
                        .placeholder(errorId)
                        .transform(new RoundedCorners(radius)))
                .load(imgUrl)
                .into(imageView);
    }

}
