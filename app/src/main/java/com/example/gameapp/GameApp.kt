package com.example.gameapp

import android.app.Application
import com.example.gameapp.di.gameAppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GameApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GameApp)
            modules(gameAppModule)
        }
    }
}