package ru.plamit.currencytest.currencyList

import io.reactivex.Single
import ru.plamit.currencytest.api.ICurrencyApi
import ru.plamit.currencytest.entity.CurrencyRates

class CurrencyInteractor(private val api: ICurrencyApi) : ICurrencyInteractor {
    override fun getCurrencies(baseCurrency: String): Single<CurrencyRates> = api.getLatestCurrencies(baseCurrency)
}