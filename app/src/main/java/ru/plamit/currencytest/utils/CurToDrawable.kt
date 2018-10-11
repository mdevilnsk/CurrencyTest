package ru.plamit.currencytest.utils

import ru.plamit.currencytest.R

/**
 * Converts currency name to image drawable icon
 * @param cur - currency name like USD
 * @return - ResDrawable integer value with vector drawable icon
 */
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
        "KRW" -> R.drawable.kor
        "MXN" -> R.drawable.mex
        "MYR" -> R.drawable.mys
        "NOK" -> R.drawable.nor
        "NZD" -> R.drawable.nzl
        "PHP" -> R.drawable.phl
        "PLN" -> R.drawable.pol
        "RON" -> R.drawable.rou
        "RUB" -> R.drawable.rus
        "SEK" -> R.drawable.swe
        "SGD" -> R.drawable.sgp
        "THB" -> R.drawable.tha
        "TRY" -> R.drawable.tur
        "ZAR" -> R.drawable.zwe
        "EUR" -> R.drawable.eur
        "USD" -> R.drawable.usa
        else -> R.drawable.rus
    }
}
