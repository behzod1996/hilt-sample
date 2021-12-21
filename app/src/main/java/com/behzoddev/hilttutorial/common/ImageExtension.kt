package com.behzoddev.hilttutorial.common

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.NonNull
import com.behzoddev.hilttutorial.R
import com.squareup.picasso.Picasso

fun ImageView.loadUrl(
    @NonNull url: String,
    placeHolder: Drawable = this.context.getDrawable(R.drawable.ic_launcher_foreground)!!,
    error: Drawable = this.context.getDrawable(R.drawable.ic_launcher_background)!!
) {
    Picasso.get()
        .load(url)
        .placeholder(placeHolder)
        .error(error)
        .into(this)
}