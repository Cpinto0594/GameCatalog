package com.cpinto.gamecatalog.modules.api

import com.cpinto.gamecatalog.modules.api.datasources.games.GamesDataSource
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

@Module
class DataSourceProvider {
    @Provides
    @Reusable
    fun providerGamesApi(retrofit: Retrofit): GamesDataSource =
        retrofit.create(GamesDataSource::class.java)
}