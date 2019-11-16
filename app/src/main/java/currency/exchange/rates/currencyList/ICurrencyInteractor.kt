package currency.exchange.rates.currencyList

import io.reactivex.Single
import currency.exchange.rates.entity.CountryInfo
import currency.exchange.rates.entity.CurrencyRates

/**
 * Conteact for interactor (use case)
 */
interface ICurrencyInteractor{
    /**
     * get currencies rate
     * @param baseCurrency - base currency for getting rate
     */
    fun getCurrencies(baseCurrency: String, force: Boolean = false): Single<CurrencyRates>

    /**
     * get countries information
     */
    fun getCountriesInfo(): Single<List<CountryInfo?>>
}