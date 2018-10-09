package ru.plamit.currencytest.di

import org.koin.dsl.module.module
import ru.plamit.currencytest.api.RetrofitBuilder

val currencyModule = module {
    single { RetrofitBuilder().createApi() }
}