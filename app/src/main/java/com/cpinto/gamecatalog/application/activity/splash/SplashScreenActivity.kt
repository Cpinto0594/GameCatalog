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

class SplashScreenActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.splash_screen_activity

    override fun initUi() {
        validateSplash()
    }

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