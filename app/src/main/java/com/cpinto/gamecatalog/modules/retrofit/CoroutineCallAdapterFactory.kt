package com.cpinto.gamecatalog.modules.retrofit

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.*
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class CoroutineCallAdapterFactory private constructor() : CallAdapter.Factory() {

    companion object {
        @JvmStatic
        @JvmName("create")
        operator fun invoke() = CoroutineCallAdapterFactory()
    }

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Deferred::class.java != getRawType(returnType)) {
            return null
        }

        check(returnType is ParameterizedType) { "Deferred return type must be parameterized as Deferred<Foo> or Deferred<out Foo>" }
        val responseType = getParameterUpperBound(0, returnType)

        val rawDeferredType = getRawType(responseType)
        return if (rawDeferredType == ApiResponse::class.java) {
            check(responseType is ParameterizedType) { "Response must be parameterized as Response<Foo> or Response<out Foo>" }
            ResponseCallAdapter<Any>(getParameterUpperBound(0, responseType))
        } else {
            BodyCallAdapter<Any>(responseType)
        }
    }

    private class BodyCallAdapter<T>(private val responseType: Type) :
        CallAdapter<T, Deferred<ApiResponse<T>>> {

        override fun responseType() = responseType

        override fun adapt(call: Call<T>): Deferred<ApiResponse<T>> {
            val deferred = CompletableDeferred<ApiResponse<T>>()

            deferred.invokeOnCompletion {
                if (deferred.isCancelled) {
                    call.cancel()
                }
            }

            call.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    if (t is IOException) {
                        deferred.complete(ApiResponse.create(t))
                    } else {
                        deferred.completeExceptionally(t)
                    }
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful) {
                        deferred.complete(ApiResponse.create(response))
                    } else {
                        deferred.completeExceptionally(HttpException(response))
                    }
                }
            })

            return deferred
        }
    }

    private class ResponseCallAdapter<T>(private val responseType: Type) :
        CallAdapter<T, Deferred<ApiResponse<T>>> {

        override fun responseType() = responseType

        override fun adapt(call: Call<T>): Deferred<ApiResponse<T>> {

            val deferred = CompletableDeferred<ApiResponse<T>>()

            deferred.invokeOnCompletion {
                if (deferred.isCancelled) {
                    call.cancel()
                }
            }

            call.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    if (t is IOException) {
                        deferred.complete(ApiResponse.create(t))
                    } else {
                        deferred.completeExceptionally(t)
                    }
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    deferred.complete(ApiResponse.create(response))
                }
            })

            return deferred
        }
    }
}
