package com.irfan.auto1.manufacturers.data.remote

import com.irfan.auto1.BuildConfig
import com.irfan.auto1.manufacturers.data.remote.api.ManufacturersRemoteAPI
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class Networking private constructor(){
    companion object{
        private lateinit var instance: Networking
        fun getInstance(): Networking {
            if(!Companion::instance.isInitialized){
                instance = Networking()
            }
            return instance
        }
    }



    fun provideRetrofit(basUrl: String,okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(basUrl)
            .client(okHttpClient)
            .build()
    }

    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(interceptor).build()
    }

    fun provideBasUrl():String{
        return BuildConfig.BAS_URL
    }

    fun provideMoviesApi(retrofit: Retrofit): ManufacturersRemoteAPI =
        retrofit.create(ManufacturersRemoteAPI::class.java)

    fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->

            val original = chain.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("wa_key", BuildConfig.URL_TOKEN)
                .build()

            val reqBuilder = original.newBuilder()
                .url(url)
            chain.proceed(reqBuilder.build())
        }
    }
}