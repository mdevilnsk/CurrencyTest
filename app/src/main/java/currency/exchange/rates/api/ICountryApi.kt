package currency.exchange.rates.api

import io.reactivex.Single
import retrofit2.http.GET
import currency.exchange.rates.entity.CountryInfo

interface ICountryApi{

    companion object {
        const val COUNTRY_URL = "https://plamit.ru/"
    }

    /**
     * get countries information - country, flag, currency
     */
    @GET("countries/")
    fun getCountriesInfo(): Single<List<CountryInfo>>
}