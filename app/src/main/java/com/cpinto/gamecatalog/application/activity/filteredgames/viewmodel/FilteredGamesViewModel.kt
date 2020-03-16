package com.cpinto.gamecatalog.application.activity.filteredgames.viewmodel

import android.content.Context
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.activity.filteredgames.adapters.FilteredGamesAdapter
import com.cpinto.gamecatalog.application.activity.games.adapter.GamesCardAdapter
import com.cpinto.gamecatalog.application.models.games.DataToPersist
import com.cpinto.gamecatalog.application.models.games.Games
import com.cpinto.gamecatalog.modules.viewmodelmodule.BaseViewModel
import javax.inject.Inject

class FilteredGamesViewModel @Inject constructor() : BaseViewModel() {

    lateinit var arrGames: MutableList<Games>
    private lateinit var filteredGamesAdapter: FilteredGamesAdapter
    private lateinit var gameClickListener: GamesCardAdapter.GameClickListener

    fun createFilteredAdapter(
        gameClickListener: GamesCardAdapter.GameClickListener
    ): FilteredGamesAdapter {
        this.gameClickListener = gameClickListener
        filteredGamesAdapter = FilteredGamesAdapter(this)
        return filteredGamesAdapter
    }

    fun initFilteredData() {
        arrGames = DataToPersist.arrGames
        filteredGamesAdapter.setData(arrGames)
    }

    private fun getGameByPosition(position: Int): Games =
        arrGames[position]

    fun getSectionName(context: Context): String =
        context.getString(R.string.filtered_title, filteredGamesAdapter.itemCount.toString())

    fun getGameName(position: Int): String =
        getGameByPosition(position).name

    fun getGameUniverse(position: Int): String =
        getGameByPosition(position).universe

    fun getGameImageUrl(position: Int): String =
        getGameByPosition(position).imageURL

    fun onGameClicked(position: Int) {
        gameClickListener.onGameClick(getGameByPosition(position))
    }
}