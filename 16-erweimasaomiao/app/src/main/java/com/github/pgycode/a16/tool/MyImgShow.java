package com.github.pgycode.a16.tool;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.pgycode.a16.R;

import javax.xml.transform.Templates;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * Created by rtyui on 2018/4/25.
 */

public class MyImgShow {

    public static void showNetImgCircle(Context context, String url, ImageView img){
        Glide.with(context)
                .load(url)
                .error(R.drawable.placehoder)
                .placeholder(R.drawable.error)
                .override(200, 200)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(img);
    }


    public static void showLocalImgSquare(Context context, String url, ImageView img){
        Glide.with(context)
                .load(url)
                .asBitmap()
                .error(R.drawable.error)
                .placeholder(R.drawable.placehoder)
                .dontAnimate()
                .override(200, 200)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .fitCenter()
                .into(img);
    }

    public static void showCompleteImgSquare(Context context, String url, ImageView img){
        Glide.with(context)
                .load(url)
                .error(R.drawable.placehoder)
                .placeholder(R.drawable.error)
                .dontAnimate()
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .fitCenter()
                .into(img);
    }

//    public static void showNetImgCircle(Fragment fragment, String url, ImageView img){
//        Glide.with(fragment)
//                .load(url)
//                .error(R.drawable.temp)
//                .override(140, 140)
//                .placeholder(R.drawable.temp)
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .into(img);
//    }

    public static void showNetImgSquare(Context context, String url, ImageView img){
        Glide.with(context)
                .load(url)
                .dontAnimate()
                .override(100, 75)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(img);
    }

    public static void showNetImgCircle(Activity activity, String url, ImageView img){
        Glide.with(activity)
                .load(url)
                .error(R.drawable.error)
                .override(140, 140)
                .placeholder(R.drawable.placehoder)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(img);
    }

    public static void showNetImgCircle(FragmentActivity activity, String url, ImageView img){
        Glide.with(activity)
                .load(url)
                .error(R.drawable.error)
                .override(140, 140)
                .placeholder(R.drawable.placehoder)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(img);
    }

}
