package com.arabin.retrofit.restapihelper

import com.arabin.retrofit.BuildConfig
import com.arabin.retrofit.api.schoolapi.SchoolApiHelperImpl
import com.arabin.retrofit.api.schoolapi.SchoolApiHelperInterface
import com.arabin.retrofit.apiservice.SchoolApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author Arabin
 * @since 12/16/2022
 * @singleton provides api-service
 * builds retrofit with logger
 * Hilt module
 * [MVVM] architecture should
 * communicate with repo only
 * */
@Module
@InstallIn(SingletonComponent::class)
class RestAPIBuilderModule {

    /**Provides the url declared in gradle of this retrofit module*/
    @Provides
    fun provideSchoolBaseUrl() = BuildConfig.SCHOOL_BASE_URL

    /**Provides Api helper interface to call server*/
    @Provides
    @Singleton
    fun getSchoolApiService(schoolApiHelper: SchoolApiHelperImpl): SchoolApiHelperInterface = schoolApiHelper

    /**Provides api service once baseUrl building done*/
    @Provides
    @Singleton
    fun buildSchoolApi(): SchoolApiService = buildRestApi(provideSchoolBaseUrl()).create(SchoolApiService::class.java)

    /**Builds the retrofit use Gson converter used*/
    @Provides
    @Singleton
    fun buildRestApi(aUrl: String): Retrofit {
        return Retrofit.Builder().baseUrl(aUrl).addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient())
            .build()
    }

    /**Creates OKHTTP client with logger intercepted*/
    @Provides
    @Singleton
    fun okhttpClient(): OkHttpClient {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }


}