package com.cpinto.gamecatalog.modules.api

import com.cpinto.gamecatalog.modules.api.datasources.games.GamesRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class RepositoryProvider {

    @Provides
    @Reusable
    fun providerGamesRepository(dataSource: GamesRepository.GamesNetwork): GamesRepository = dataSource
}