package ru.plamit.currencytest.entity

import com.google.gson.annotations.SerializedName

data class CountryInfo(@SerializedName("flag")
                       val flag: String = "",
                       @SerializedName("name")
                       val name: String = "",
                       @SerializedName("currencies")
                       val currencies: List<CurrenciesItem>?)

data class CurrenciesItem(@SerializedName("symbol")
                          val symbol: String = "",
                          @SerializedName("code")
                          val code: String = "",
                          @SerializedName("name")
                          val name: String = "")


