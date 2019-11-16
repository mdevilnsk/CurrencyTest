package ru.plamit.currencytest.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.plamit.currencytest.entity.CountryInfo

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