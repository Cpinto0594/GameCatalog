package com.cpinto.gamecatalog.dagger.injector

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.cpinto.gamecatalog.Application
import dagger.android.AndroidInjection
import dagger.android.HasActivityInjector
import dagger.android.support.AndroidSupportInjection

object AppInjector {
    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object :
            android.app.Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handlerActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {}

            override fun onActivityResumed(activity: Activity) {}

            override fun onActivityPaused(activity: Activity) {}

            override fun onActivityStopped(activity: Activity) {}

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {}

            override fun onActivityDestroyed(activity: Activity) {}
        })
    }

    private fun handlerActivity(activity: Activity) {
        if (activity is HasActivityInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentCreated(
                        fm: FragmentManager,
                        f: Fragment,
                        savedInstanceState: Bundle?
                    ) {
//                        if (f is Injectable) {
                       // AndroidSupportInjection.inject(f)
//                        }
                    }
                }, true
            )
        }
    }
}