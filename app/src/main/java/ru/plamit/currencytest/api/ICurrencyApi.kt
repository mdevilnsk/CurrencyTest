package ru.plamit.currencytest.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

interface ICurrencyApi{

    companion object {
        const val BASE_URL = "https://revolut.duckdns.org/"
    }

    @GET("latest")
    fun getLatestCurrencies(@Path ("base") base: String): Observable<Currency>
}