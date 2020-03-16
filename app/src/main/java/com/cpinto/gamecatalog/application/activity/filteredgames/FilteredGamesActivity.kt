package com.cpinto.gamecatalog.application.activity.filteredgames

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.activity.BaseActivity
import com.cpinto.gamecatalog.application.activity.filteredgames.viewmodel.FilteredGamesViewModel
import com.cpinto.gamecatalog.application.activity.gamedetail.GameDetailActivity
import com.cpinto.gamecatalog.application.activity.games.adapter.GamesCardAdapter
import com.cpinto.gamecatalog.application.models.games.Constants
import com.cpinto.gamecatalog.application.models.games.FilterOptions
import com.cpinto.gamecatalog.application.models.games.Games
import kotlinx.android.synthetic.main.filtered_games_actitivy.*
import kotlinx.android.synthetic.main.games_headerbar_filtered_layout.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.parceler.Parcels


/**
 *
 * FilteredGamesActivity
 *
 * This class is the view for Filtered Games
 * TODO:
 * Apply filters and Loading
 *
 * @author Carlos Pinto
 * @version 1
 * @since 1.0
 *
 */
class FilteredGamesActivity : BaseActivity(), GamesCardAdapter.GameClickListener {

    private val viewModel: FilteredGamesViewModel by viewModels {
        viewModelFactory
    }

    private val filteredOptions: FilterOptions by lazy {
        Parcels.unwrap<FilterOptions>(
            intent.getParcelableExtra(
                Constants.KEYS.FILTERS
            )
        )
    }

    override fun setLayout(): Int = R.layout.filtered_games_actitivy

    override fun initUi() {
        initHeader()
        createAdapter()
        dataObserver()
        initData()

    }

    /**
     * this method handles the data change event and updates the view
     */
    private fun dataObserver() {
        viewModel.dataObserver.observe(this, Observer {
            labelFilteredGames.text =
                getString(R.string.filtered_title, viewModel.arrGames.size.toString())
        })
    }

    /**
     * this method loads the data to be presented
     * @see FilteredGamesViewModel#initFilteredData
     */
    private fun initData() {
        viewModel.initFilteredData(filteredOptions)
    }


    /**
     * this method initializes the adapter for the recyclerview
     * @see FilteredGamesViewModel#createFilteredAdapter
     */
    private fun createAdapter() {
        recyclerGames.adapter = viewModel.createFilteredAdapter(this)
    }

    /**
     * this method initializes the header parameters
     */
    private fun initHeader() {
        txtHeaderBarTitle.text = getString(R.string.title_games)
        setSupportActionBar(toolbar)
        txtHeaderBarClose.onClick { super.onBackPressed() }
    }

    /**
     * this method habdles the click on a game card and opens Games detail Activity
     * @param game
     * @see GameDetailActivity
     */
    override fun onGameClick(game: Games) {
        startActivity(
            intentFor<GameDetailActivity>(
                Constants.KEYS.GAME to Parcels.wrap(game)
            ).newTask()
        )
    }

}