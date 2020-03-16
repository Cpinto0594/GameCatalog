package com.cpinto.gamecatalog.modules.api.datasources.games

import com.cpinto.gamecatalog.application.models.games.GamesResult
import com.cpinto.gamecatalog.modules.retrofit.ApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface GamesDataSource {
    @GET("Product")
    fun getGamesAsync(): Deferred<ApiResponse<GamesResult>>
}