package com.irfan.auto1.common

import org.koin.dsl.module

val networkModule = module {
    val networking = Networking.getInstance()
    factory { networking.provideInterceptor() }
    factory { networking.provideOkHttpClient(get()) }

    factory { networking.provideBasUrl() }
    single { networking.provideRetrofit(get(), get()) }
}