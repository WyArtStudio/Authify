package com.wahyuhw.authify.base

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.jakewharton.threetenabp.AndroidThreeTen
import com.wahyuhw.authify.di.networkModule
import com.wahyuhw.authify.di.preferenceModule
import com.wahyuhw.authify.di.repositoryModule
import com.wahyuhw.authify.di.useCaseModule
import com.wahyuhw.authify.di.viewModelModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level.NONE
import org.koin.core.module.Module

@HiltAndroidApp
class AuthifyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        startKoin {
            androidLogger(NONE)
            androidContext(this@AuthifyApplication)
            modules(getModules())
        }
    }
    
    private fun getModules(): List<Module> =
        listOf(
            networkModule,
            repositoryModule,
            useCaseModule,
            viewModelModule,
            preferenceModule
        )
}