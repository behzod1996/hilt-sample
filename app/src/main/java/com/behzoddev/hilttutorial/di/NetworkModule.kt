package com.behzoddev.hilttutorial.di

import com.behzoddev.hilttutorial.BuildConfig
import com.behzoddev.hilttutorial.network.PhotoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        client: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ) : PhotoService{
        return Retrofit.Builder().run {
            baseUrl(BuildConfig.BASE_URL)
            addConverterFactory(moshiConverterFactory)
            client(client)
            build()
        }.run {
            create(PhotoService::class.java)
        }
    }
    @Provides
    @Singleton
    fun provideMoshiInstance() : MoshiConverterFactory{
        return MoshiConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideOKHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient().apply {
        OkHttpClient.Builder().run {
            addInterceptor(loggingInterceptor)
            build()
        }
    }
    @Provides
    @Singleton
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

}