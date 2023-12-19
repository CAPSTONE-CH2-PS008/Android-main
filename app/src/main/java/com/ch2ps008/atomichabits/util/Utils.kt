package com.ch2ps008.atomichabits.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun formatHour(hour: Int): String {
    return String.format("%02d.00", hour)
}

fun loadImage(context: Context, url: String, imageView: ImageView) {
    Glide.with(context)
        .load(url)
        .into(imageView)
}
