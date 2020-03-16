package com.cpinto.gamecatalog.dagger.component

import android.app.Application
import android.content.Context
import com.cpinto.gamecatalog.dagger.binder.AppBinder
import com.cpinto.gamecatalog.modules.api.DataSourceProvider
import com.cpinto.gamecatalog.modules.api.RepositoryProvider
import com.cpinto.gamecatalog.modules.viewmodelmodule.ViewModelModule
import com.cpinto.gamecatalog.modules.retrofit.RetrofitInterceptor
import com.cpinto.gamecatalog.modules.retrofit.RetrofitModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RetrofitModule::class,
        AndroidSupportInjectionModule::class,
        ViewModelModule::class,
        DataSourceProvider::class,
        RepositoryProvider::class,
        AppBinder::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(application: com.cpinto.gamecatalog.Application)
    fun inject(retrofitInterceptor: RetrofitInterceptor)

}