package ru.plamit.currencytest

import android.app.Application
import org.koin.core.context.startKoin
import ru.plamit.currencytest.di.currencyModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(currencyModule)
        }
    }
}