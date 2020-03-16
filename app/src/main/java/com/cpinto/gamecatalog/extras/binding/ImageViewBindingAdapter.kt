package com.cpinto.gamecatalog.extras.binding

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.cpinto.gamecatalog.application.extension_funtions.loadFromUrl

object ImageViewBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "imageRounded", "roundedRadius"], requireAll = false)
    fun loadImageUrl(
        imageView: AppCompatImageView,
        imageUrl: String,
        imageRounded: Boolean = false,
        roundedRadius: Int
    ) {
        val roundedradius = if (roundedRadius <= 0) 16 else roundedRadius
        imageView.loadFromUrl(url = imageUrl, rounded = imageRounded, radius = roundedradius)

    }

}