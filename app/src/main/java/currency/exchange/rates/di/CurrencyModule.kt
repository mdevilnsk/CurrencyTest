package currency.exchange.rates.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import currency.exchange.rates.api.RetrofitBuilder
import currency.exchange.rates.currencyList.CurrencyInteractor
import currency.exchange.rates.currencyList.ICurrencyInteractor
import currency.exchange.rates.currencyList.ui.CurrencyViewModel
import currency.exchange.rates.utils.DefaultScheduler
import currency.exchange.rates.utils.IDefaultScheduler

val currencyModule = module {
    single { RetrofitBuilder().createApi() }
    single { DefaultScheduler() as IDefaultScheduler }
    single { CurrencyInteractor(get(), get()) as ICurrencyInteractor }
    viewModel { CurrencyViewModel(get()) }
}