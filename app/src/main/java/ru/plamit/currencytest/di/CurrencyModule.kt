package ru.plamit.currencytest.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.plamit.currencytest.api.RetrofitBuilder
import ru.plamit.currencytest.currencyList.CurrencyInteractor
import ru.plamit.currencytest.currencyList.ICurrencyInteractor
import ru.plamit.currencytest.currencyList.ui.CurrencyViewModel
import ru.plamit.currencytest.utils.DefaultScheduler
import ru.plamit.currencytest.utils.IDefaultScheduler

val currencyModule = module {
    single { RetrofitBuilder().createApi() }
    single { RetrofitBuilder().createCountryApi() }
    single { DefaultScheduler() as IDefaultScheduler }
    single { CurrencyInteractor(get(), get(), get()) as ICurrencyInteractor }
    viewModel { CurrencyViewModel(get()) }
}