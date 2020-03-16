package com.cpinto.gamecatalog.application.activity.gamedetail.helper

import android.content.Context
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.models.games.Games
import java.text.DecimalFormat

class GameHelper(val context: Context, val game: Games) {
    fun getSku(): String = context.getString(R.string.game_detail_sku, game.sku)

    fun getDownloads(): String = context.getString(
        R.string.game_detail_downloads,
        DecimalFormat("#,###").format(game.downloads.toFloat())
    )

    fun getFormattedPrice(): String = "$${game.price}"

    fun getRating(): Float = game.rating.toFloat()
}