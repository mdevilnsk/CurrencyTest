package ru.plamit.currencytest.entity

import com.google.gson.annotations.SerializedName

data class CountryInfo(@SerializedName("flag")
                       val flag: String = "",
                       @SerializedName("name")
                       val name: String = "",
                       var code: String = "")