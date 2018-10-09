package ru.plamit.currencytest.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.plamit.currencytest.entity.CurrencyRates

interface ICurrencyApi{

    companion object {
        const val BASE_URL = "https://revolut.duckdns.org/"
    }

    @GET("latest")
    fun getLatestCurrencies(@Query ("base") base: String): Observable<CurrencyRates>
}