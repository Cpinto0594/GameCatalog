package com.cpinto.gamecatalog.modules.retrofit

import com.cpinto.gamecatalog.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RetrofitModule {
    @Provides
    @Singleton
    fun gson(): Gson = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    @Provides
    @Singleton
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(client())
        .build()

    private fun client(): OkHttpClient = OkHttpClient
        .Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(RetrofitInterceptor())
        .build()
}
