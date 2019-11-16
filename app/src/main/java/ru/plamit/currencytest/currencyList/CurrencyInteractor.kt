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

    private val countryList: MutableList<CountryInfo?> = ArrayList()
    private var currencyRates: CurrencyRates? = null

    override fun getCurrencies(baseCurrency: String, force: Boolean): Single<CurrencyRates> {
        if (currencyRates == null || force) {
            return api.getLatestCurrencies(baseCurrency)
                    .map {
                        currencyRates = it
                        it
                    }
                    .compose(scheduler.applySingle())
        } else {
            return Single.just(currencyRates)
        }
    }

    override fun getCountriesInfo(): Single<List<CountryInfo?>> {
        if (countryList.isNotEmpty() && countryList.none { it == null }) return Single.just(countryList)
        return countryApi.getCountriesInfo()
                .map {
                    countryList.clear()
                    countryList.addAll(it)
                    countryList
                }
                .compose(scheduler.applySingle())
    }
}