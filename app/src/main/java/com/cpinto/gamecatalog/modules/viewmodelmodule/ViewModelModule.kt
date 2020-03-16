package com.cpinto.gamecatalog.modules.viewmodelmodule


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cpinto.gamecatalog.application.activity.filteredgames.viewmodel.FilteredGamesViewModel
import com.cpinto.gamecatalog.application.activity.games.viewmodel.MainGamesViewModel
import com.cpinto.gamecatalog.application.activity.gamesfilter.viewmodel.GamesFilterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun binViewModelFactory(factory: ViewModelFactory):
            ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainGamesViewModel::class)
    abstract fun bindViewModelMainGames(mainGamesViewModel: MainGamesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GamesFilterViewModel::class)
    abstract fun bindViewModelGamesFilter(gamesFilterViewModel: GamesFilterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FilteredGamesViewModel::class)
    abstract fun bindViewFilteredGames(filteredGamesViewModel: FilteredGamesViewModel): ViewModel

}