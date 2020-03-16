package com.cpinto.gamecatalog.modules.api.datasources.games

import android.content.Context
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.models.games.Games
import com.cpinto.gamecatalog.application.models.games.GamesResult
import com.cpinto.gamecatalog.modules.retrofit.ApiResult
import com.cpinto.gamecatalog.modules.retrofit.ApiSuccessResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface GamesRepository {
    suspend fun getAllGames(): ApiResult<GamesResult>

    class GamesNetwork @Inject constructor(private val dataSource: GamesDataSource) :
        GamesRepository {
        @Inject
        lateinit var context: Context

        override suspend fun getAllGames(): ApiResult<GamesResult> =
            withContext(Dispatchers.Default) {
                val response = when (
                    val apiResult = dataSource.getGamesAsync().await()
                    ) {
                    is ApiSuccessResponse -> ApiResult.Success(apiResult.data)
                    else -> ApiResult.Error(Exception(context.getString(R.string.connectivity_failure)))
                }
                response
            }

    }
}