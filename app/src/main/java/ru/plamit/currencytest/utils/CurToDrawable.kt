package ru.plamit.currencytest.utils

import android.support.annotation.DrawableRes
import ru.plamit.currencytest.R

fun currToDrawable(cur: String): Int {
    return when (cur) {
        "AUD" -> R.drawable.aus
        "ISK" -> R.drawable.isl
        "BGN" -> R.drawable.bgr
        "BRL" -> R.drawable.bra
        "CAD" -> R.drawable.can
        "CHF" -> R.drawable.che
        "CNY" -> R.drawable.chn
        "CZK" -> R.drawable.cze
        "DKK" -> R.drawable.dnk
        "GPB" -> R.drawable.gbr
        "HKD" -> R.drawable.hkg
        "HRK" -> R.drawable.hrv
        "HUF" -> R.drawable.hun
        "IDR" -> R.drawable.idn
        "ILS" -> R.drawable.isr
        "INR" -> R.drawable.ind
        "JPY" -> R.drawable.jpn
        "MXN" -> R.drawable.kor
        "MYR" -> R.drawable.mex
        "NOK" -> R.drawable.mys
        "NZD" -> R.drawable.nor
        "PHP" -> R.drawable.nzl
        "PLN" -> R.drawable.phl
        "RON" -> R.drawable.pol
        "RUB" -> R.drawable.rou
        "SEK" -> R.drawable.rus
        "SGD" -> R.drawable.swe
        "THB" -> R.drawable.sgp
        "TRY" -> R.drawable.tha
        "ZAR" -> R.drawable.tur
        "EUR" -> R.drawable.zwe
        "USD" -> R.drawable.eur
        else -> R.drawable.rus
    }
}
