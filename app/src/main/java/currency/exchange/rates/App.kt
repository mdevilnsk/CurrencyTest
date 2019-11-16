package currency.exchange.rates

import android.app.Application
import org.koin.core.context.startKoin
import currency.exchange.rates.di.currencyModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(currencyModule)
        }
    }
}