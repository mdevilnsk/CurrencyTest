package ru.plamit.currencytest.entity

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class CurrencyRates(@SerializedName("date")
                         val date: String = "",
                         @SerializedName("rates")
                         val rates: Map<String, BigDecimal>,
                         @SerializedName("base")
                         val base: String = "")


