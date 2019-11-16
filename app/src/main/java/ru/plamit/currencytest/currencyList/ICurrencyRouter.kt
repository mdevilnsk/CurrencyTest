package ru.plamit.currencytest.currencyList

import androidx.annotation.StringRes

/**
 * router interface
 */
interface ICurrencyRouter{
    /**
     * route to show error
     */
    fun routeToError(@StringRes message: Int)
}