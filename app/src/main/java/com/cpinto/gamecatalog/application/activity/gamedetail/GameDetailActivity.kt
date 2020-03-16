package com.cpinto.gamecatalog.application.activity.gamedetail

import android.os.Parcel
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.activity.BaseActivity
import com.cpinto.gamecatalog.application.activity.gamedetail.helper.GameHelper
import com.cpinto.gamecatalog.application.models.games.Constants
import com.cpinto.gamecatalog.application.models.games.Games
import com.cpinto.gamecatalog.databinding.GameDetailActivityBinding
import kotlinx.android.synthetic.main.games_headerbar_detail_layout.*
import org.jetbrains.anko.longToast
import org.parceler.Parcels

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

    private fun shareGame() {
        longToast(game.description)
    }

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