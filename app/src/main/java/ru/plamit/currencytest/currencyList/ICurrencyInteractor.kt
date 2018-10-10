package ru.plamit.currencytest.currencyList

import io.reactivex.Single
import ru.plamit.currencytest.entity.CurrencyRates

/**
 * Conteact for interactor (use case)
 */
interface ICurrencyInteractor{

    fun getCurrencies(baseCurrency: String): Single<CurrencyRates>

}