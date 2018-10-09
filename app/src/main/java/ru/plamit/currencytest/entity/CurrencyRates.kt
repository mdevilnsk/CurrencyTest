package ru.plamit.currencytest.entity

import com.google.gson.annotations.SerializedName


data class Rates(@SerializedName("CHF")
                 val chf: Double = 0.0,
                 @SerializedName("HRK")
                 val hrk: Double = 0.0,
                 @SerializedName("MXN")
                 val mxn: Double = 0.0,
                 @SerializedName("ZAR")
                 val zar: Double = 0.0,
                 @SerializedName("INR")
                 val inr: Double = 0.0,
                 @SerializedName("CNY")
                 val cny: Double = 0.0,
                 @SerializedName("THB")
                 val thb: Double = 0.0,
                 @SerializedName("AUD")
                 val aud: Double = 0.0,
                 @SerializedName("ILS")
                 val ils: Double = 0.0,
                 @SerializedName("KRW")
                 val krw: Double = 0.0,
                 @SerializedName("PLN")
                 val pln: Double = 0.0,
                 @SerializedName("GBP")
                 val gbp: Double = 0.0,
                 @SerializedName("IDR")
                 val idr: Double = 0.0,
                 @SerializedName("HUF")
                 val huf: Double = 0.0,
                 @SerializedName("PHP")
                 val php: Double = 0.0,
                 @SerializedName("TRY")
                 val tryCur: Double = 0.0,
                 @SerializedName("RUB")
                 val rub: Double = 0.0,
                 @SerializedName("HKD")
                 val hkd: Double = 0.0,
                 @SerializedName("ISK")
                 val isk: Double = 0.0,
                 @SerializedName("EUR")
                 val eur: Double = 0.0,
                 @SerializedName("DKK")
                 val dkk: Double = 0.0,
                 @SerializedName("CAD")
                 val cad: Double = 0.0,
                 @SerializedName("MYR")
                 val myr: Double = 0.0,
                 @SerializedName("USD")
                 val usd: Double = 0.0,
                 @SerializedName("BGN")
                 val bgn: Double = 0.0,
                 @SerializedName("NOK")
                 val nok: Double = 0.0,
                 @SerializedName("RON")
                 val ron: Double = 0.0,
                 @SerializedName("SGD")
                 val sgd: Double = 0.0,
                 @SerializedName("CZK")
                 val czk: Double = 0.0,
                 @SerializedName("SEK")
                 val sek: Double = 0.0,
                 @SerializedName("NZD")
                 val nzd: Double = 0.0,
                 @SerializedName("BRL")
                 val brl: Double = 0.0,
                 @SerializedName("JPY")
                 val jpy: Double = 0.0)


data class Currency(@SerializedName("date")
                    val date: String = "",
                    @SerializedName("rates")
                    val rates: Rates,
                    @SerializedName("base")
                    val base: String = "")


