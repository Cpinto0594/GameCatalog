package com.cpinto.gamecatalog.application.activity.filteredgames

import androidx.activity.viewModels
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.activity.BaseActivity
import com.cpinto.gamecatalog.application.activity.filteredgames.viewmodel.FilteredGamesViewModel
import com.cpinto.gamecatalog.application.activity.gamedetail.GameDetailActivity
import com.cpinto.gamecatalog.application.activity.games.adapter.GamesCardAdapter
import com.cpinto.gamecatalog.application.models.games.Constants
import com.cpinto.gamecatalog.application.models.games.Games
import kotlinx.android.synthetic.main.filtered_games_actitivy.*
import kotlinx.android.synthetic.main.games_headerbar_filtered_layout.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.parceler.Parcels

class FilteredGamesActivity : BaseActivity(), GamesCardAdapter.GameClickListener {

    private val viewModel: FilteredGamesViewModel by viewModels {
        viewModelFactory
    }

    override fun setLayout(): Int = R.layout.filtered_games_actitivy

    override fun initUi() {
        initHeader()
        createAdapter()
        initData()
    }

    private fun initData() {
        viewModel.initFilteredData()
        labelFilteredGames.text =
            getString(R.string.filtered_title, viewModel.arrGames.size.toString())
    }

    private fun createAdapter() {
        recyclerGames.adapter = viewModel.createFilteredAdapter(this)
    }

    private fun initHeader() {
        txtHeaderBarTitle.text = getString(R.string.title_games)
        setSupportActionBar(toolbar)
        txtHeaderBarClose.onClick { super.onBackPressed() }
    }

    override fun onGameClick(game: Games) {
        startActivity(
            intentFor<GameDetailActivity>(
                Constants.KEYS.GAME to Parcels.wrap(game)
            ).newTask()
        )
    }

}