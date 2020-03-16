package com.cpinto.gamecatalog

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.couchbase.lite.CouchbaseLite
import com.cpinto.gamecatalog.dagger.component.AppComponent
import com.cpinto.gamecatalog.dagger.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class Application : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector


    companion object {
        lateinit var daggerAppComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        this.configureInjectComponent()
        this.initCouchLiteDB()
    }

    private fun configureInjectComponent() {
        daggerAppComponent = DaggerAppComponent.builder()
            .application(this)
            .context(this)
            .build()
        daggerAppComponent.inject(this)
    }

    private fun initCouchLiteDB() {
        CouchbaseLite.init(this)

    }

}
