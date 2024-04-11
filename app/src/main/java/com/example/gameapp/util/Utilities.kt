package com.example.gameapp.util

import android.content.Context
import android.widget.Toast
import com.example.gameapp.di.gameAppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

fun startKoinApplication(context: Context) {
    startKoin {
        androidContext(context)
        modules(gameAppModule)
    }
}

fun showToast(text: String, context: Context) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}