package com.cpinto.gamecatalog.application.activity.gamesfilter.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.activity.gamesfilter.adapter.GamesFilterAdapter
import com.cpinto.gamecatalog.application.activity.gamesfilter.holders.GameFilterPropsHolder
import com.cpinto.gamecatalog.application.activity.gamesfilter.holders.GamesFilterHolderData
import com.cpinto.gamecatalog.application.models.games.FilterOptions
import com.cpinto.gamecatalog.application.models.games.GameCategories
import com.cpinto.gamecatalog.db.couchlite.CouchDatabase
import com.cpinto.gamecatalog.modules.viewmodelmodule.BaseViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import javax.inject.Inject


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
class GamesFilterViewModel @Inject constructor() : BaseViewModel(),
    GamesFilterAdapter.SectionClickListener {


    private lateinit var gamesFilterAdapter: GamesFilterAdapter
    //this variable should contain the filter values
    private var gamesFilterOptions: FilterOptions = FilterOptions()
    private val sectionClickObserver: MutableLiveData<GamesFilterHolderData> = MutableLiveData()


    /**
     * this method creates the recycler view adapter for Filter Sections
     * @param context
     * @return GamesFilterAdapter
     */
    fun createFilterAdapter(context: Context): GamesFilterAdapter {
        gamesFilterAdapter = GamesFilterAdapter(context, this)
        return gamesFilterAdapter
    }

    /**
     * this method creates the filter sections
     * @param context
     * @see addPropsSections
     * @see addSeparatorSection
     * @see addRatingSection
     * @see addCategoriesSection
     */
    fun createView(context: Context) {
        val sections: MutableList<GamesFilterHolderData> = arrayListOf()
        ioScope.launch {
            addPropsSections(context, sections)
            addSeparatorSection(sections)
            addRatingSection(sections)
            addSeparatorSection(sections)
            addCategoriesSection(sections)
            gamesFilterAdapter.setData(sections)
        }
    }

    /**
     * this method adds game properties sort section
     * @param context
     * @param sections
     */
    private fun addPropsSections(context: Context, sections: MutableList<GamesFilterHolderData>) {
        sections.apply {
            add(
                GamesFilterHolderData(
                    type = GamesFilterAdapter.GAME_PROP,
                    title = context.getString(R.string.downloads_title),
                    value = GameFilterPropsHolder.DOWNLOADS
                )
            )
            add(
                GamesFilterHolderData(
                    type = GamesFilterAdapter.GAME_PROP,
                    title = context.getString(R.string.date_added_title),
                    value = GameFilterPropsHolder.DATE_ADDED
                )
            )
            add(
                GamesFilterHolderData(
                    type = GamesFilterAdapter.GAME_PROP,
                    title = context.getString(R.string.price_title),
                    value = GameFilterPropsHolder.DOWNLOADS
                )
            )
        }
    }

    /**
     * this method adds separator view to filter sections
     * @param sections
     */
    private fun addSeparatorSection(sections: MutableList<GamesFilterHolderData>) {
        sections.apply {
            add(GamesFilterHolderData(type = GamesFilterAdapter.SEPARATOR))
        }
    }

    /**
     * this method adds Rating filter to filter sections
     * @param sections
     */
    private fun addRatingSection(sections: MutableList<GamesFilterHolderData>) {
        for (step in 5 downTo 1) {
            sections.apply {
                add(
                    GamesFilterHolderData(
                        type = GamesFilterAdapter.GAME_RATING,
                        title = "$step",
                        value = "$step"
                    )
                )
            }
        }
    }

    /**
     * this method load categories from the local DB
     * ***** This method should be in DB repository class
     * @return data array of Categories
     */
    private fun fetchLocalCategories(): MutableList<GameCategories> {
        val database = CouchDatabase.getInstance()
        val docId = "categories"
        val doc = database.getDocument(docId) ?: return mutableListOf()
        val resultArray = doc.getString("results")
        val type = object : TypeToken<List<GameCategories>>() {}.type
        return Gson().fromJson(resultArray, type)
    }

    /**
     * this method adds Categories filter to filter sections
     * @param sections
     */
    private fun addCategoriesSection(sections: MutableList<GamesFilterHolderData>) {
        val categories = fetchLocalCategories()
        categories.forEach { category ->
            sections.add(
                GamesFilterHolderData(
                    type = GamesFilterAdapter.GAME_UNIVERSE,
                    title = category.name,
                    value = category.name
                )
            )
        }
    }

    private fun getSectionByPosition(position: Int): GamesFilterHolderData =
        gamesFilterAdapter.data[position]

    fun sectionTextTitle(position: Int): String =
        getSectionByPosition(position).title ?: run { "" }

    override fun onSectionClickListener(position: Int) {
        sectionClickObserver.value = getSectionByPosition(position)
    }

    fun sectionPropsCheckedValue(position: Int): Boolean = false

    fun sectionRatinGetStep(position: Int): Int =
        getSectionByPosition(position).value.toString().toInt()

}