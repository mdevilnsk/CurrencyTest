package ru.plamit.currencytest.currencyList

import io.reactivex.Single
import ru.plamit.currencytest.api.ICountryApi
import ru.plamit.currencytest.api.ICurrencyApi
import ru.plamit.currencytest.entity.CountryInfo
import ru.plamit.currencytest.entity.CurrencyRates
import ru.plamit.currencytest.utils.IDefaultScheduler

class CurrencyInteractor(
        private val api: ICurrencyApi,
        private val countryApi: ICountryApi,
        private val scheduler: IDefaultScheduler
) : ICurrencyInteractor {

    private val countryList: MutableList<CountryInfo> = ArrayList()
    override fun getCurrencies(baseCurrency: String): Single<CurrencyRates> =
            api.getLatestCurrencies(baseCurrency)
                    .compose(scheduler.applySingle())

    override fun getCountryInfoByCurrency(currency: String): Single<CountryInfo> {
        val info = countryList.find { it.code == currency }
        if (info != null) return Single.just(info)
        return countryApi.getCountryInfoByCurrency(currency)
                .map {
                    it[0].code = currency
                    countryList.add(it[0])
                    it[0]
                }.compose(scheduler.applySingle())
    }
}