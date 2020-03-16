package com.cpinto.gamecatalog.application.activity.gamesfilter.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.activity.gamesfilter.adapter.GamesFilterAdapter
import com.cpinto.gamecatalog.application.activity.gamesfilter.holders.GameFilterCategoryHolder
import com.cpinto.gamecatalog.application.activity.gamesfilter.holders.GameFilterPropsHolder
import com.cpinto.gamecatalog.application.activity.gamesfilter.holders.GamesFilterHolderData
import com.cpinto.gamecatalog.application.models.games.DataToPersist
import com.cpinto.gamecatalog.application.models.games.FilterOptions
import com.cpinto.gamecatalog.application.models.games.GameCategories
import com.cpinto.gamecatalog.application.models.games.Games
import com.cpinto.gamecatalog.db.couchlite.CouchDatabase
import com.cpinto.gamecatalog.modules.viewmodelmodule.BaseViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import javax.inject.Inject

class GamesFilterViewModel @Inject constructor() : BaseViewModel(),
    GamesFilterAdapter.SectionClickListener {


    private lateinit var gamesFilterAdapter: GamesFilterAdapter
    private var gamesFilterOptions: FilterOptions = FilterOptions()
    private val sectionClickObserver: MutableLiveData<GamesFilterHolderData> = MutableLiveData()


    fun createFilterAdapter(context: Context): GamesFilterAdapter {
        gamesFilterAdapter = GamesFilterAdapter(context, this)
        return gamesFilterAdapter
    }

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

    private fun addSeparatorSection(sections: MutableList<GamesFilterHolderData>) {
        sections.apply {
            add(GamesFilterHolderData(type = GamesFilterAdapter.SEPARATOR))
        }
    }

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

    private fun fetchLocalCategories(): MutableList<GameCategories> {
        val database = CouchDatabase.getInstance()
        val docId = "categories"
        val doc = database.getDocument(docId) ?: return mutableListOf()
        val resultArray = doc.getString("results")
        val type = object : TypeToken<List<GameCategories>>() {}.type
        return Gson().fromJson(resultArray, type)
    }

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

    fun getSectionByPosition(position: Int): GamesFilterHolderData =
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