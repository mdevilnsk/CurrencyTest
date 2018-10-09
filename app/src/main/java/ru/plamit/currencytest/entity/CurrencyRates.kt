package ru.plamit.currencytest.entity

import com.google.gson.annotations.SerializedName

data class CurrencyRates(@SerializedName("date")
                         val date: String = "",
                         @SerializedName("rates")
                         val rates: Map<String, Double>,
                         @SerializedName("base")
                         val base: String = "")


