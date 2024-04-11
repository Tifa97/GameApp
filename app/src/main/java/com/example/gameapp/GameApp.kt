package com.example.gameapp

import android.app.Application
import com.example.gameapp.di.gameAppModule
import com.example.gameapp.util.startKoinApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GameApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoinApplication(this@GameApp)
    }
}