package com.oguzhanozgokce.healthandprediction.common

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

object ImageLoader {
    fun loadImage(url: String?, imageView: ImageView, @DrawableRes placeholderResId: Int) {
        if (url.isNullOrEmpty()) {
            imageView.setImageResource(placeholderResId)
        } else {
            Glide.with(imageView)
                .load(url)
                .placeholder(placeholderResId)
                .error(placeholderResId)
                .into(imageView)
        }
    }
}