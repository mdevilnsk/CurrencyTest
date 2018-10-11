package ru.plamit.currencytest.currencyList

import android.support.annotation.StringRes

interface ICurrencyRouter{
    fun routeToError(@StringRes message: Int)
}