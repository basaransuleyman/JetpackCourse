package com.example.jetpack.utils

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.jetpack.R

//Extensions

fun ImageView.downloadFromUrl(url: String?, progressDrawable: CircularProgressDrawable) {

    val options = RequestOptions()
        .placeholder(placeholderProgressBar(context)) //placeholder until coming the image
        .error(R.mipmap.ic_launcher_round)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

fun placeholderProgressBar(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }

}

@BindingAdapter("android:downlaodUrl")
fun downloadImage(view: ImageView, url: String?) {
    view.downloadFromUrl(url, placeholderProgressBar(view.context))
}