package currency.exchange.rates

import currency.exchange.rates.di.currencyModule
import org.koin.core.context.startKoin

class App : com.squareup.picasso.App() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(currencyModule)
        }
    }
}