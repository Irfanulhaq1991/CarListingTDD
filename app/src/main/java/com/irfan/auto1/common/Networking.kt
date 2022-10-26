package com.irfan.auto1.common

import com.irfan.auto1.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import okhttp3.logging.HttpLoggingInterceptor


class Networking private constructor() {
    companion object {
        private lateinit var instance: Networking
        fun getInstance(): Networking {
            if (!Companion::instance.isInitialized) {
                instance = Networking()
            }
            return instance
        }
    }


    fun provideRetrofit(basUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(basUrl)
            .client(okHttpClient)
            .build()
    }

    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient().newBuilder()
            .addInterceptor(logging)
            .addInterceptor(interceptor).build()
    }

    fun provideBasUrl(): String {
        return BuildConfig.BAS_URL
    }



    fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->

            val original = chain.request()
            val originalHttpUrl = original.url

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("wa_key", BuildConfig.URL_TOKEN)
                .build()

            val reqBuilder = original.newBuilder()
                .url(url)
            chain.proceed(reqBuilder.build())
        }
    }
}