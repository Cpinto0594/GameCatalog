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
import kotlinx.android.synthetic.main.main_games_activity.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.longToast
import org.jetbrains.anko.newTask
import org.jetbrains.anko.startActivityForResult
import org.parceler.Parcels


/**
 *
 * MainGamesActivity
 *
 * This class is the view for MainGamesView
 *
 * @author Carlos Pinto
 * @version 1
 * @since 1.0
 *
 */
class MainGamesActivity : BaseActivity(), GamesCardAdapter.GameClickListener {

    companion object {
        const val RESULT_FILTER = 1
    }

    private val viewModel: MainGamesViewModel by viewModels {
        viewModelFactory
    }

    override fun setLayout(): Int = R.layout.main_games_activity

    /**
     * This method initializes the view component and events
     * @see initDataRequest
     * @see initGamesAdapter
     * @see initGamesObserver
     * @see initHeader
     * @see initSwipeLayout
     * @see onLoadingObserver
     */
    override fun initUi() {
        initHeader()
        initSwipeLayout()
        onLoadingObserver()
        initGamesAdapter()
        initGamesObserver()
        initDataRequest()
    }

    /**
     * this method handles the visibility of login view
     */
    private fun onLoadingObserver() {
        viewModel.isLoading.observe(this, Observer {
            progressBar.isVisible = it
        })
    }

    /**
     * this method handles the SwipeEvent and refreshes the data from remote endpoint
     * @see fetchGamesFromRemote
     */
    private fun initSwipeLayout() {
        swippeLayout.setOnRefreshListener { fetchGamesFromRemote() }
    }

    /**
     * this methid initializes fetches games from remote endpoint
     * @see MainGamesViewModel#getGamesRemote
     */
    private fun fetchGamesFromRemote() {
        viewModel.getGamesRemote()
        swippeLayout.isRefreshing = false
    }

    /**
     * this method initializes the adapter for Horizontal Cards
     * @see MainGamesViewModel#createSectionCategoriesAdapter
     * @see MainGamesViewModel#createRecyclerGamesAdapter
     */
    private fun initGamesAdapter() {
        recyclerGamesCategories.adapter = viewModel.createSectionCategoriesAdapter(this)
        recyclerGames.adapter = viewModel.createRecyclerGamesAdapter(this, this)
    }

    private fun initGamesObserver() {
        viewModel.errorObserver.observe(this, Observer {
            longToast(it.toString())
        })
    }

    /**
     * This method initializes the games and categories request
     */
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