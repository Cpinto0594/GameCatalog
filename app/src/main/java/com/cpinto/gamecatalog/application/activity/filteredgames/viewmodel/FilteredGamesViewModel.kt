package com.cpinto.gamecatalog.application.activity.filteredgames.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.activity.filteredgames.adapters.FilteredGamesAdapter
import com.cpinto.gamecatalog.application.activity.games.adapter.GamesCardAdapter
import com.cpinto.gamecatalog.application.activity.gamesfilter.holders.GameFilterPropsHolder
import com.cpinto.gamecatalog.application.models.games.FilterOptions
import com.cpinto.gamecatalog.application.models.games.Games
import com.cpinto.gamecatalog.db.couchlite.CouchDatabase
import com.cpinto.gamecatalog.modules.viewmodelmodule.BaseViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

/**
 *
 * FilteredGamesViewModel
 *
 * This class is the backend for FilteredGamesActivity
 *
 * @author Carlos Pinto
 * @version 1
 * @since 1.0
 *
 */
class FilteredGamesViewModel @Inject constructor() : BaseViewModel() {

    lateinit var arrGames: MutableList<Games>
    private lateinit var filteredGamesAdapter: FilteredGamesAdapter
    private lateinit var gameClickListener: GamesCardAdapter.GameClickListener
    val dataObserver: MutableLiveData<MutableList<Games>> = MutableLiveData()
    lateinit var filterOptions: FilterOptions


    /**
     * this method creates the recycler view for Games
     * @param gameClickListener GamesCardAdapter#GameClickListener
     * @return FilteredGamesAdapter
     */
    fun createFilteredAdapter(
        gameClickListener: GamesCardAdapter.GameClickListener
    ): FilteredGamesAdapter {
        this.gameClickListener = gameClickListener
        filteredGamesAdapter = FilteredGamesAdapter(this)
        return filteredGamesAdapter
    }

    /**
     * this method fetches the data from local DB
     */
    fun initFilteredData(filters: FilterOptions) {
        filterOptions = filters
        ioScope.launch {
            var games = fetchLocalGames()
            games = applyFilterToGames(games)
            uiScope.launch {
                arrGames = games
                dataObserver.value = arrGames
                filteredGamesAdapter.setData(arrGames)
            }
        }
    }

    /**
     * This method applies all filter options to our array of games
     * and return the resulting
     * @param games
     * @return List of filtered Games
     */
    private fun applyFilterToGames(games: MutableList<Games>): MutableList<Games> {
        var initialGames = games.toMutableList()
        //Games Sorting
        filterOptions.selectedSortingProperty?.let {
            initialGames.sortWith(
                when (filterOptions.selectedSortingProperty.toString().toInt()) {
                    GameFilterPropsHolder.DOWNLOADS -> downloadsComparator
                    GameFilterPropsHolder.DATE_ADDED -> creationComparator
                    else -> priceComparator
                }
            )
        }
        //Filter by Stars if selected
        if (filterOptions.selectedStarsFilter.isNotEmpty()) {
            initialGames =
                initialGames.filter {
                    filterOptions.selectedStarsFilter.contains(it.rating)
                } as MutableList<Games>
        }
        //Filter by Category if selected
        filterOptions.selectedCategoryFilter?.let {
            initialGames =
                initialGames.filter {
                    it.universe == filterOptions.selectedCategoryFilter.toString()
                } as MutableList<Games>
        }

        return initialGames
    }

    //Sort By Downloads
    private val downloadsComparator: Comparator<Games> =
        compareByDescending { it.downloads.toFloat() }
    //Sort by Creation Date
    private val creationComparator: Comparator<Games> = compareByDescending { it.createdAt.time }
    //Sort by Price
    private val priceComparator: Comparator<Games> =
        compareByDescending {
            it.price.replace(
                ",",
                DecimalFormat().decimalFormatSymbols.decimalSeparator.toString()
            ).toFloat()
        }


    /**
     * this method load games from the local DB
     * ***** This method should be in DB repository class
     * @return data array of Games
     */
    private fun fetchLocalGames(): MutableList<Games> {
        val database = CouchDatabase.getInstance()
        val docId = "games"
        val doc = database.getDocument(docId) ?: return mutableListOf()
        val resultArray = doc.getString("results")
        val type = object : TypeToken<List<Games>>() {}.type
        return Gson().fromJson(resultArray, type)
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