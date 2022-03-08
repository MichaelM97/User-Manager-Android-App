package com.michaelmccormick.usermanager.network.di

import com.google.gson.Gson
import com.michaelmccormick.usermanager.network.factories.ServiceFactory
import com.michaelmccormick.usermanager.network.interfaces.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Singleton
    @Provides
    fun provideUserService(): UserService =
        ServiceFactory(
            baseUrl = "https://gorest.co.in/public/v2/",
            okHttpClientBuilder = OkHttpClient.Builder(),
            retrofitBuilder = Retrofit.Builder(),
            gsonConverterFactory = GsonConverterFactory.create(),
        ).build()
}
