package com.cpinto.gamecatalog.application.extension_funtions

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.modules.glide.GlideApp

internal fun AppCompatImageView.loadFromUrl(
    url: String?,
    drawable: Int = R.drawable.empty_product,
    rounded: Boolean = false,
    radius: Int = 16
) {

    var styles = RequestOptions()
        .placeholder(drawable)
        .error(drawable)
    if (rounded) {
        styles = styles.transforms(RoundedCorners(radius))
    }
    GlideApp.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .apply(styles)
        .into(this)
}