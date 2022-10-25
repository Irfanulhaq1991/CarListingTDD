package com.irfan.auto1.common

import com.irfan.auto1.manufacturers.data.remote.Networking
import org.koin.dsl.module

val networkModule = module {
    val networking = Networking.getInstance()
    factory { networking.provideInterceptor() }
    factory { networking.provideOkHttpClient(get()) }
    factory { networking.provideMoviesApi(get()) }
    factory { networking.provideBasUrl() }
    single { networking.provideRetrofit(get(), get()) }
}