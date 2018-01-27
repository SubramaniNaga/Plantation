package com.fresh.mind.plantation.Constant;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.fresh.mind.plantation.R;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by AND I5 on 27-01-2018.
 */

public class ImageUtils {


    /**
     * SetImages
     *
     * @param imageView
     * @param url
     * @param context
     */
    public static void setImage(ImageView imageView, String url, Context context) {
        if (url != null) {
            Picasso.with(context).load((Uri.fromFile(new File(url)))).resize(70, 70).error(R.drawable.no_thumbnail).placeholder(R.drawable.no_thumbnail).into(imageView);
        }
    }

}
