package com.cpinto.gamecatalog.application.fonts

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class SanFranciscoBold(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {
    init {
        typeface =
            Typeface.createFromAsset(context.assets, "fonts/SanFranciscoText/San_Francisco_Bold.ttf")
    }
}