package com.michaelmccormick.usermanager.network.factories

import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class ServiceFactory(
    private val baseUrl: String,
    okHttpClientBuilder: OkHttpClient.Builder,
    private val retrofitBuilder: Retrofit.Builder,
    private val gsonConverterFactory: GsonConverterFactory,
) {
    private val client = okHttpClientBuilder
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    inline fun <reified T> build(): T {
        return retrofitBuilder
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(T::class.java)
    }
}
