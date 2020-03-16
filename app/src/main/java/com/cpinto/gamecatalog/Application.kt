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


/**
 *
 * Application
 *
 * This class initializes Dagger DI
 *
 * @author Carlos Pinto
 * @version 1
 * @since 1.0
 *
 */
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

    /**
     * This method creates Dagger Component
     */
    private fun configureInjectComponent() {
        daggerAppComponent = DaggerAppComponent.builder()
            .application(this)
            .context(this)
            .build()
        daggerAppComponent.inject(this)
    }

    /**
     * this method initializes the BD
     */
    private fun initCouchLiteDB() {
        CouchbaseLite.init(this)

    }

}
