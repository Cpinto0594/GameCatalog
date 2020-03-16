package com.cpinto.gamecatalog.application.activity.gamesfilter

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.activity.BaseActivity
import com.cpinto.gamecatalog.application.activity.filteredgames.FilteredGamesActivity
import com.cpinto.gamecatalog.application.activity.gamesfilter.viewmodel.GamesFilterViewModel
import com.cpinto.gamecatalog.application.models.games.Constants
import kotlinx.android.synthetic.main.game_filters_activity.*
import kotlinx.android.synthetic.main.games_headerbar_filter_layout.*
import org.jetbrains.anko.intentFor
import org.parceler.Parcels

/**
 *
 * GameFiltersActivity
 *
 * This class is the view for Filtered Games
 *
 * @author Carlos Pinto
 * @version 1
 * @since 1.0
 *
 */
class GameFiltersActivity : BaseActivity() {

    private val viewModel: GamesFilterViewModel by viewModels { viewModelFactory }

    override fun setLayout(): Int = R.layout.game_filters_activity

    /**
     * This method initializes view components and events
     * @see initHeader
     * @see createGameFilterAdapter
     * @see drawSections
     * @see applyFilterButton
     */
    override fun initUi() {
        initHeader()
        createGameFilterAdapter()
        drawSections()
        onDatasetChanged()
        applyFilterButton()
    }

    private fun onDatasetChanged() {
        viewModel.dataSetChangedObserver.observe(this, Observer {
            recyclerViewFilterSections.post {
                viewModel.gamesFilterAdapter.notifyDataSetChanged()
            }
        })
    }

    /**
     * this method handles the event of apply filters button
     * @see applyFilters
     */
    private fun applyFilterButton() {
        buttonApplyFilters.setOnClickListener { applyFilters() }
    }


    /**
     * this method opens Filter result activity
     * @see FilteredGamesActivity
     */
    private fun applyFilters() {
        startActivity(
            intentFor<FilteredGamesActivity>(
                Constants.KEYS.FILTERS to Parcels.wrap(viewModel.gamesFilterOptions)
            )
        )
        finish()
    }

    /**
     * this method draws filter sections
     * @see GamesFilterViewModel#createView
     */
    private fun drawSections() {
        viewModel.createView(this)
    }

    /**
     * this method creates the adapter for the recyclerView
     * @see GamesFilterViewModel#createFilterAdapter
     */
    private fun createGameFilterAdapter() {
        recyclerViewFilterSections.adapter = viewModel.createFilterAdapter(this)
    }

    /**
     * this method initializes the header components
     * @see setSupportActionBar
     * @see statusBarColorGrayFilters
     */
    private fun initHeader() {
        setSupportActionBar(toolbar)
        txtHeaderBarTitle.text = getString(R.string.filters_title)
        txtHeaderBarClose.setOnClickListener { super.onBackPressed() }
        statusBarColorGrayFilters()
    }
}