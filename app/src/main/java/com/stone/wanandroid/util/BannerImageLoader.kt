package com.stone.wanandroid.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-02
 */
class BannerImageLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        Glide.with(context)
            .load(path.toString())
            .into(imageView)

    }
}