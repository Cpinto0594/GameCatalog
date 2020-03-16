package com.cpinto.gamecatalog.application.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.cpinto.gamecatalog.R
import com.cpinto.gamecatalog.lifecycle.LifeCycleProvider
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val activityScopeCoroutine = CoroutineScope(Dispatchers.Main)
    protected val lifecycleOwner = LifeCycleProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayout())
        //lifecycleOwner.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        initUi()
    }

    fun statusBarColorWhite() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = ContextCompat.getColor(this, R.color.white)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    fun statusBarColorGrayFilters() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = ContextCompat.getColor(this, R.color.header_bar_filter)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    protected abstract fun setLayout(): Int
    protected abstract fun initUi()
}