package com.cpinto.gamecatalog.application.activity.games.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.couchbase.lite.*
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.activity.games.adapter.GamesCardAdapter
import com.cpinto.gamecatalog.application.activity.games.adapter.HorizontalGamesCategoriesAdapter
import com.cpinto.gamecatalog.application.activity.games.adapter.MainGamesAdapter
import com.cpinto.gamecatalog.application.models.games.*
import com.cpinto.gamecatalog.db.couchlite.CouchDatabase
import com.cpinto.gamecatalog.modules.api.datasources.games.GamesRepository
import com.cpinto.gamecatalog.modules.retrofit.ApiResult
import com.cpinto.gamecatalog.modules.viewmodelmodule.BaseViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainGamesViewModel @Inject constructor(private val repository: GamesRepository) :
    BaseViewModel() {

    companion object {
        const val CATEGORY_ALL = "All"
    }

    @Inject
    lateinit var context: Context

    private lateinit var recyclerGamesAdapter: MainGamesAdapter
    lateinit var horizontalCategoriesAdapter: HorizontalGamesCategoriesAdapter
    private var selectedCategory: GameCategories = GameCategories()
    val gamesObserver: MutableLiveData<MutableList<Games>> = MutableLiveData()
    val errorObserver: MutableLiveData<String> = MutableLiveData()
    private lateinit var gameClickListener: GamesCardAdapter.GameClickListener
    private var gameSections: MutableList<GamesAdapterDefinition> = mutableListOf()
    val gamesArray: MutableList<Games> = mutableListOf()
    val categoriesArray: MutableList<GameCategories> = mutableListOf()

    fun createRecyclerGamesAdapter(
        context: Context,
        gameClickListener: GamesCardAdapter.GameClickListener
    ): MainGamesAdapter {
        this.gameClickListener = gameClickListener
        recyclerGamesAdapter = MainGamesAdapter(context, this)
        return recyclerGamesAdapter
    }

    fun createSectionCategoriesAdapter(context: Context): HorizontalGamesCategoriesAdapter {
        val adapter = HorizontalGamesCategoriesAdapter(context, this)
        horizontalCategoriesAdapter = adapter
        return adapter
    }

    fun createSectionGamesAdapter(parent: Int): GamesCardAdapter {
        val adapter = GamesCardAdapter(this, parent)
        adapter.setData(gameSections[parent].arrGames)
        return adapter
    }

    private fun cleanEmptyGamesData(data: MutableList<Games>) {
        data.removeAll { it.imageURL.isNullOrEmpty() }
    }

    fun checkGames() {
        ioScope.launch {
            handleLoadingObserver(true)
            val localGames = fetchLocalGames()
            if (localGames.isNotEmpty()) {
                val localCategories = fetchLocalCategories()
                uiScope.launch { processLocalGames(localGames, localCategories) }
            } else {
                getGamesRemote()
            }
        }
    }


    fun getGamesRemote() {
        uiScope.launch {
            handleLoadingObserver(true)
            when (val response = repository.getAllGames()) {
                is ApiResult.Success<GamesResult> -> processGames(response.data)
                is ApiResult.Error -> processError(response.exception)
            }
        }
    }

    private fun processError(exception: Exception) {
        errorObserver.value = exception.message
        handleLoadingObserver(false)
    }

    private fun processLocalGames(
        arrGames: MutableList<Games>,
        arrCategories: MutableList<GameCategories>
    ) {
        selectedCategory = GameCategories()
        cleanEmptyGamesData(arrGames)
        gamesArray.clear()
        gamesArray.addAll(arrGames)
        categoriesArray.clear()
        categoriesArray.add(GameCategories(CATEGORY_ALL))
        categoriesArray.addAll(arrCategories)
        gamesObserver.value = arrGames
        applyCategoryFilter()
        handleLoadingObserver(false)
    }

    private fun processGames(data: GamesResult) {
        selectedCategory = GameCategories()
        cleanEmptyGamesData(data.results)
        persistData(data) {
            gamesObserver.value = gamesArray
            applyCategoryFilter()
            handleLoadingObserver(false)
        }
    }

    private fun applyCategoryFilter(selectedCategory: GameCategories? = null) {
        gameSections = selectedCategory?.let {
            filterDataByCategory(selectedCategory)
        } ?: run {
            applyInitialData()
        }
        recyclerGamesAdapter.setData(gameSections)
        horizontalCategoriesAdapter.setData(categoriesArray)
    }

    private fun applyInitialData(): MutableList<GamesAdapterDefinition> = arrayListOf(
        GamesAdapterDefinition(
            type = 1,
            typeName = context.getString(R.string.new_games_title),
            arrGames = getNewGames(gamesArray)
        ),
        GamesAdapterDefinition(
            type = 1,
            typeName = context.getString(R.string.popular_games_title),
            arrGames = getPopularGames(gamesArray)
        )
    )

    private fun filterDataByCategory(selectedCategory: GameCategories): MutableList<GamesAdapterDefinition> {
        //Category ALL shows all games
        return when (selectedCategory.name) {
            CATEGORY_ALL -> {
                val sections = applyInitialData()
                sections.add(
                    GamesAdapterDefinition(
                        type = 1,
                        typeName = "$CATEGORY_ALL ( ${gamesArray.size} )",
                        arrGames = gamesArray
                    )
                )
                sections
            }
            else -> {
                //Filter by single category
                val filteredGames =
                    gamesArray.filter { it.universe == selectedCategory.name } as MutableList<Games>
                mutableListOf(
                    GamesAdapterDefinition(
                        type = 1,
                        typeName = "${selectedCategory.name} ( ${filteredGames.size} )",
                        arrGames = filteredGames
                    )
                )
            }
        }

    }


    private fun persistData(data: GamesResult, afterPersist: () -> Unit) {
        ioScope.launch { persistInDb(data) }
        afterPersist()
    }

    private fun getNewGames(data: MutableList<Games>): MutableList<Games> {
        val initialData: MutableList<Games> = mutableListOf()
        initialData.addAll(data)
        initialData.sortWith(compareByDescending { it.createdAt.time })
        return initialData.take(5) as MutableList<Games>
    }

    private fun getPopularGames(data: MutableList<Games>): MutableList<Games> {
        val initialData: MutableList<Games> = mutableListOf()
        initialData.addAll(data)
        val filteredData = initialData.filter { it.popular }
        return filteredData.take(5) as MutableList<Games>
    }


    private fun getSectionGamesCount(position: Int): Int = gameSections[position].arrGames.size

    fun getSectionName(position: Int, context: Context): String =
        String.format(gameSections[position].typeName, getSectionGamesCount(position))

    private fun getGameByPosition(parent: Int, position: Int): Games =
        gameSections[parent].arrGames[position]

    fun getGameName(parent: Int, position: Int): String =
        getGameByPosition(parent, position).name

    fun getGameUniverse(parent: Int, position: Int): String =
        getGameByPosition(parent, position).universe

    fun getGameImageUrl(parent: Int, position: Int): String =
        getGameByPosition(parent, position).imageURL

    fun onGameClicked(parent: Int, position: Int) {
        gameClickListener.onGameClick(getGameByPosition(parent, position))
    }

    private fun getCategoryByPosition(position: Int): GameCategories =
        horizontalCategoriesAdapter.data[position]

    fun getCategoryName(position: Int): String = getCategoryByPosition(position).name

    fun isCategorySelected(position: Int): Boolean =
        getCategoryByPosition(position).name == selectedCategory.name

    fun onSelectCategory(position: Int) {
        selectedCategory = getCategoryByPosition(position)
        horizontalCategoriesAdapter.notifyDataSetChanged()
        applyCategoryFilter(selectedCategory)
    }

    private fun getGamesStringToPersistInDb(data: GamesResult): String = Gson().toJson(data.results)

    private fun getGamesCategoriesStringToPersistInDb(data: GamesResult): String {
        return Gson().toJson(getCategoriesFromGames(data.results))
    }

    private fun saveGamesInDB(map: Map<String, String>): Boolean {
        val database = CouchDatabase.getInstance()
        val gamesDoc = "games"
        return try {
            val doc = MutableDocument(gamesDoc, map)
            database.save(doc)
            true
        } catch (ex: java.lang.Exception) {
            println(ex.message)
            false
        }
    }

    private fun saveCategoriesInDB(map: Map<String, String>): Boolean {
        val database = CouchDatabase.getInstance()
        val categoriesDoc = "categories"
        return try {
            val doc = MutableDocument(categoriesDoc, map)
            database.save(doc)
            true
        } catch (ex: java.lang.Exception) {
            println(ex.message)
            false
        }
    }

    private fun getCategoriesFromGames(arrGames: MutableList<Games>): MutableList<GameCategories> =
        arrGames.map { it.universe }
            .distinct()
            .map { GameCategories(name = it) } as MutableList<GameCategories>

    private fun persistInDb(data: GamesResult) {
        val database = CouchDatabase.getInstance()
        val gamesDoc = "games"
        val categoriesDoc = "categories"

        database.deleteIndex(gamesDoc)
        database.deleteIndex(categoriesDoc)

        val gamesString = getGamesStringToPersistInDb(data)
        val categoriesString = getGamesCategoriesStringToPersistInDb(data)

        saveGamesInDB(hashMapOf("results" to gamesString))
        saveCategoriesInDB(hashMapOf("results" to categoriesString))

        gamesArray.clear()
        gamesArray.addAll(data.results)
        categoriesArray.clear()
        categoriesArray.add(GameCategories(CATEGORY_ALL))
        categoriesArray.addAll(getCategoriesFromGames(gamesArray))
    }

    private fun fetchLocalGames(): MutableList<Games> {
        val database = CouchDatabase.getInstance()
        val docId = "games"
        val doc = database.getDocument(docId) ?: return mutableListOf()
        val resultArray = doc.getString("results")
        val type = object : TypeToken<List<Games>>() {}.type
        return Gson().fromJson(resultArray, type)
    }

    private fun fetchLocalCategories(): MutableList<GameCategories> {
        val database = CouchDatabase.getInstance()
        val docId = "categories"
        val doc = database.getDocument(docId) ?: return mutableListOf()
        val resultArray = doc.getString("results")
        val type = object : TypeToken<List<GameCategories>>() {}.type
        return Gson().fromJson(resultArray, type)
    }


}