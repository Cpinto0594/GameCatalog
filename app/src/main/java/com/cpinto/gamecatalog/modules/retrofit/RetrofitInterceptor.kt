package com.cpinto.gamecatalog.modules.retrofit

import android.content.Context
import com.cpinto.gamecatalog.Application
import com.cpinto.gamecatalog.BuildConfig
import com.cpinto.gamecatalog.R
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class RetrofitInterceptor : Interceptor {

    @Inject
    lateinit var context: Context

    init {
        Application.daggerAppComponent.inject(this)
    }

    companion object {
        private val X_Parse_Application_Id =
            Pair("X-Parse-Application-Id", BuildConfig.X_Parse_Application_Id)
        private val X_Parse_REST_API_Key =
            Pair("X-Parse-REST-API-Key", BuildConfig.X_Parse_REST_API_Key)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return kotlin.runCatching {
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader(X_Parse_Application_Id.first, X_Parse_Application_Id.second)
                    .addHeader(X_Parse_REST_API_Key.first, X_Parse_REST_API_Key.second)
                    .build()
            )
        }.onFailure {
            throw IOException(context.getString(R.string.connectivity_failure))
        }.getOrThrow()
    }
}