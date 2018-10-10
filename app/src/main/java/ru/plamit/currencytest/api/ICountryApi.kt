package ru.plamit.currencytest.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import ru.plamit.currencytest.entity.CountryInfo

interface ICountryApi{

    companion object {
        const val COUNTRY_URL = "https://restcountries.eu/"
    }

    @GET("rest/v2/currency/{currency}")
    fun getCountryInfoByCurrency(@Path("currency") currencyName: String): Single<List<CountryInfo>>
}