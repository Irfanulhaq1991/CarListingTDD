package com.irfan.auto1.common

import android.app.Application
import com.irfan.auto1.manufacturers.di.manufacturerModule
import com.irfan.auto1.model.di.modelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Koin Android logger
            androidLogger(Level.ERROR)
            //inject Android context
            androidContext(this@App)
            // use modules
            modules( manufacturerModule, modelModule, networkModule)
        }
    }

}