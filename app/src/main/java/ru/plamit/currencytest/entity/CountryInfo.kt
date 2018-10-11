package ru.plamit.currencytest.entity

import com.google.gson.annotations.SerializedName

data class CountryInfo(@SerializedName("flag")
                       val flag: String = "",
                       @SerializedName("name")
                       val name: String = "",
                       @SerializedName("cur_code")
                       val currencyCode: String = "",
                       @SerializedName("cur_name")
                       val currencyName: String = "")


