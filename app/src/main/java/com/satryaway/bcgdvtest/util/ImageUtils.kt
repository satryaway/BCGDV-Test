package com.satryaway.bcgdvtest.util

import android.widget.ImageView
import com.squareup.picasso.Picasso

class ImageUtils {
    companion object {
        fun loadImage(url: String, imageView: ImageView) {
            Picasso.get().load(url).into(imageView)
        }
    }
}