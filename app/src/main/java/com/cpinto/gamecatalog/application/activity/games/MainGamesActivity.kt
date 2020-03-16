package com.cpinto.gamecatalog.application.activity.games

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.activity.BaseActivity
import com.cpinto.gamecatalog.application.activity.gamedetail.GameDetailActivity
import com.cpinto.gamecatalog.application.activity.games.adapter.GamesCardAdapter
import com.cpinto.gamecatalog.application.activity.games.viewmodel.MainGamesViewModel
import com.cpinto.gamecatalog.application.activity.gamesfilter.GameFiltersActivity
import com.cpinto.gamecatalog.application.models.games.Constants
import com.cpinto.gamecatalog.application.models.games.Games
import kotlinx.android.synthetic.main.games_headerbar_layout.*
import kotlinx.android.synthetic.main.main_gemes_activity.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.longToast
import org.jetbrains.anko.newTask
import org.jetbrains.anko.startActivityForResult
import org.parceler.Parcels

class MainGamesActivity : BaseActivity(), GamesCardAdapter.GameClickListener {

    companion object {
        const val RESULT_FILTER = 1
    }

    private val viewModel: MainGamesViewModel by viewModels {
        viewModelFactory
    }

    override fun setLayout(): Int = R.layout.main_gemes_activity

    override fun initUi() {
        initHeader()
        initSwipeLayout()
        onLoadingObserver()
        initGamesAdapter()
        initGamesObserver()
        initDataRequest()
    }

    private fun onLoadingObserver() {
        viewModel.isLoading.observe(this , Observer {
            progressBar.isVisible = it
        })
    }

    private fun initSwipeLayout() {
        swippeLayout.setOnRefreshListener { fetchGamesFromRemote() }
    }

    private fun fetchGamesFromRemote() {
        viewModel.getGamesRemote()
        swippeLayout.isRefreshing = false
    }

    private fun initGamesAdapter() {
        recyclerGamesCategories.adapter = viewModel.createSectionCategoriesAdapter(this)
        recyclerGames.adapter = viewModel.createRecyclerGamesAdapter(this, this)
    }

    private fun initGamesObserver() {
        viewModel.gamesObserver.observe(lifecycleOwner, Observer {
            longToast("hay datos")
        })
        viewModel.errorObserver.observe(lifecycleOwner, Observer {
            longToast(it.toString())
        })
    }


    private fun initDataRequest() {
        viewModel.checkGames()
    }

    private fun initHeader() {
        statusBarColorWhite()
        txtHeaderBarTitle.text = getString(R.string.title_games)
        headerBarFilter.setOnClickListener { openFilterScreen() }
    }

    private fun openFilterScreen() {
        startActivityForResult<GameFiltersActivity>(RESULT_FILTER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_FILTER && resultCode == Activity.RESULT_OK) {
            validateFilters()
        }
    }

    private fun validateFilters() {
        longToast("TODO")
    }

    override fun onGameClick(game: Games) {
        startActivity(
            intentFor<GameDetailActivity>(
                Constants.KEYS.GAME to Parcels.wrap(game)
            ).newTask()
        )
    }
}