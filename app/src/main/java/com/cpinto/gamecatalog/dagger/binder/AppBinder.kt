package com.cpinto.gamecatalog.dagger.binder


import com.cpinto.gamecatalog.application.activity.filteredgames.FilteredGamesActivity
import com.cpinto.gamecatalog.application.activity.gamedetail.GameDetailActivity
import com.cpinto.gamecatalog.application.activity.gamesfilter.GameFiltersActivity
import com.cpinto.gamecatalog.application.activity.games.MainGamesActivity
import com.cpinto.gamecatalog.application.activity.onboarding.OnBoardingActivity
import com.cpinto.gamecatalog.application.activity.splash.SplashScreenActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class AppBinder {

    @ContributesAndroidInjector
    internal abstract fun mainGamesActivity(): MainGamesActivity

    @ContributesAndroidInjector
    internal abstract fun splashScreenActivity(): SplashScreenActivity

    @ContributesAndroidInjector
    internal abstract fun onBoardingActivity(): OnBoardingActivity

    @ContributesAndroidInjector
    internal abstract fun gameDetailActivity(): GameDetailActivity

    @ContributesAndroidInjector
    internal abstract fun gameFiltersActivity(): GameFiltersActivity

    @ContributesAndroidInjector
    internal abstract fun filteredGamesActivity(): FilteredGamesActivity

}