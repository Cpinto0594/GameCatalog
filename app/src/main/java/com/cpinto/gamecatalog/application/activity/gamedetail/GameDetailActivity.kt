package com.cpinto.gamecatalog.application.activity.gamedetail

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.MenuItem
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.activity.BaseActivity
import com.cpinto.gamecatalog.application.activity.gamedetail.helper.GameHelper
import com.cpinto.gamecatalog.application.extension_funtions.ShareContentProvider
import com.cpinto.gamecatalog.application.models.games.Constants
import com.cpinto.gamecatalog.application.models.games.Games
import com.cpinto.gamecatalog.databinding.GameDetailActivityBinding
import kotlinx.android.synthetic.main.games_headerbar_detail_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.parceler.Parcels
import java.io.File
import java.io.FileOutputStream


/**
 *
 * GameDetailActivity
 *
 * This class is for the detail of one game
 *
 * @author Carlos Pinto
 * @version 1
 * @since 1.0
 *
 */
class GameDetailActivity : BaseActivity() {
    private val game: Games by lazy {
        Parcels.unwrap<Games>(intent.getParcelableExtra(Constants.KEYS.GAME))
    }

    override fun setLayout(): Int = R.layout.game_detail_activity

    override fun initUi() {
        initGameBinding()
        initHeader()
    }

    private fun initHeader() {
        setSupportActionBar(toolbar)
        headerBarShare.setOnClickListener { shareGame() }
    }

    /**
     * This method shares the game information
     * @see shareInformation
     * @see getImage
     */
    private fun shareGame() {
        CoroutineScope(Dispatchers.IO).launch {
            shareInformation(getImage())
        }
    }

    /**
     * this method downloads the image and save into local device
     */
    private fun getImage(): File {
        val bitmap = ShareContentProvider.imageToBitMap(this, game.imageURL)
        return ShareContentProvider.saveImageToLocal(this, bitmap, game.name, "png")
    }

    /**
     * this method creates the content to share and shares it
     */
    private fun shareInformation(image: File) {
        val gameString = StringBuilder().apply {
            append("Game: ")
            append(game.name)
            append("\n")
            append("Game SKU: ")
            append(game.sku)
            append("\n")
            append("\n")
            append("Description: ")
            append("\n")
            append(game.description)
        }
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, gameString.toString())
            putExtra(
                Intent.EXTRA_STREAM,
                FileProvider.getUriForFile(
                    this@GameDetailActivity,
                    applicationContext.packageName + ".provider",
                    image
                )
            )
            type = "image/png"
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
        image.delete()
    }

    /**
     * This method initializes the View Componen tBindings
     */
    private fun initGameBinding() {
        game.let {
            val view = DataBindingUtil.setContentView<GameDetailActivityBinding>(
                this,
                R.layout.game_detail_activity
            )
            view.game = GameHelper(this, game)
            view.executePendingBindings()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}