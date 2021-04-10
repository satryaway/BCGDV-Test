package com.satryaway.bcgdvtest.util

import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageUtils {
    companion object {
        fun loadImage(url: String, imageView: ImageView) {
            Glide.with(imageView.context).load(url).into(imageView);
        }
    }
}