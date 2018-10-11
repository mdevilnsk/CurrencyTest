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

    override fun getCountriesInfo(): Single<List<CountryInfo>> {
        if (countryList.isNotEmpty()) return Single.just(countryList)
        return countryApi.getCountriesInfo()
                .map {
                    countryList.clear()
                    countryList.addAll(it)
                    countryList
                }
                .compose(scheduler.applySingle())
    }
}