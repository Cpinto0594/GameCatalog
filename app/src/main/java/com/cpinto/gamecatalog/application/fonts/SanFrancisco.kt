package com.cpinto.gamecatalog.application.fonts

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class SanFrancisco(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {
    init {
        typeface =
            Typeface.createFromAsset(context.assets, "fonts/SanFranciscoText/San_Francisco.otf")
    }
}