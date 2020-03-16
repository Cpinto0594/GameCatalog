package com.cpinto.gamecatalog.application.activity.gamesfilter

import androidx.activity.viewModels
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.activity.BaseActivity
import com.cpinto.gamecatalog.application.activity.filteredgames.FilteredGamesActivity
import com.cpinto.gamecatalog.application.activity.gamesfilter.viewmodel.GamesFilterViewModel
import kotlinx.android.synthetic.main.game_filters_activity.*
import kotlinx.android.synthetic.main.games_headerbar_filter_layout.*
import org.jetbrains.anko.intentFor

/**
 *
 * GamesFilterViewModel
 *
 * This class is the backend for Filtered Games
 *
 * @author Carlos Pinto
 * @version 1
 * @since 1.0
 *
 */
class GameFiltersActivity : BaseActivity() {

    private val viewModel: GamesFilterViewModel by viewModels { viewModelFactory }

    override fun setLayout(): Int = R.layout.game_filters_activity

    override fun initUi() {
        initHeader()
        createGameFilterAdapter()
        drawSections()
        applyFilterButton()
    }

    private fun applyFilterButton() {
        buttonApplyFilters.setOnClickListener { applyFilters() }
    }

    private fun applyFilters() {
        startActivity(intentFor<FilteredGamesActivity>())
        finish()
    }

    private fun drawSections() {
        viewModel.createView(this)
    }

    private fun createGameFilterAdapter() {
        recyclerViewFilterSections.adapter = viewModel.createFilterAdapter(this)
    }

    private fun initHeader() {
        setSupportActionBar(toolbar)
        txtHeaderBarTitle.text = getString(R.string.filters_title)
        txtHeaderBarClose.setOnClickListener { super.onBackPressed() }
        super.statusBarColorGrayFilters()
    }
}