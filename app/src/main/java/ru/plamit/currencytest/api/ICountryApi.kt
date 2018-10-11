package ru.plamit.currencytest.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.plamit.currencytest.entity.CountryInfo

interface ICountryApi{

    companion object {
        const val COUNTRY_URL = "https://restcountries.eu/"
    }

    @GET("rest/v2/all")
    fun getCountriesInfo(@Query("fields") query: String = "name;flag;currencies"): Single<List<CountryInfo>>
}