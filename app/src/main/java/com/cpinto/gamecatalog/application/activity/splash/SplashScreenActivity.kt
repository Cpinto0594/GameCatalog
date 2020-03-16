package com.cpinto.gamecatalog.application.activity.splash

import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.activity.BaseActivity
import com.cpinto.gamecatalog.application.activity.games.MainGamesActivity
import com.cpinto.gamecatalog.application.activity.onboarding.OnBoardingActivity
import com.cpinto.gamecatalog.application.session.SessionManager
import kotlinx.coroutines.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.singleTop

/**
 *
 * SplashScreenActivity
 *
 * This class is for the splash screen view
 *
 * @author Carlos Pinto
 * @version 1
 * @since 1.0
 *
 */
class SplashScreenActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.splash_screen_activity

    override fun initUi() {
        validateSplash()
    }

    /**
     * this method validates whether is the first time opening the application
     * and shows the splash, otherwise redirects to MainGamesActivity
     */
    private fun validateSplash() {
        if (SessionManager.isFirstTimeApplication(this)) {
            activityScopeCoroutine.launch {
                delay(3000)
                initMainScreen()
            }
        }else{
            goToMainProductList()
        }
    }

    private fun initMainScreen() {
        startActivity(intentFor<OnBoardingActivity>().newTask())
        finish()
    }

    private fun goToMainProductList() {
        startActivity(intentFor<MainGamesActivity>().newTask())
        finish()
    }
}