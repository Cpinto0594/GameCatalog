package com.cpinto.gamecatalog.application.activity.onboarding

import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.application.activity.BaseActivity
import com.cpinto.gamecatalog.application.activity.games.MainGamesActivity
import com.cpinto.gamecatalog.application.activity.onboarding.adapter.ViewPagerAdapter
import com.cpinto.gamecatalog.application.session.SessionManager
import kotlinx.android.synthetic.main.onboarding_activity.*
import org.jetbrains.anko.*

class OnBoardingActivity : BaseActivity(), ViewPagerAdapter.OnPagerButtonListener {
    private lateinit var pagerAdapter: ViewPagerAdapter

    override fun setLayout(): Int = R.layout.onboarding_activity

    override fun initUi() {
        initPager()
    }

    private fun initPager() {
        pagerAdapter = ViewPagerAdapter(this, this)
        onBoardingViewPager.adapter = pagerAdapter
    }

    private fun pagerButtonEvent(position: Int, last: Boolean) {
        when (last) {
            true -> goToMainProductList()
            else -> onBoardingViewPager.currentItem = (position + 1)
        }
    }

    private fun goToMainProductList() {
        SessionManager.setFistTimeApplication(false, this)
        startActivity(intentFor<MainGamesActivity>().newTask())
        finish()
    }

    override fun onButtonClick(position: Int, lastView: Boolean) {
        pagerButtonEvent(position, lastView)
    }
}