package ru.plamit.currencytest.currencyList

import android.arch.lifecycle.MutableLiveData
import ru.plamit.currencytest.currencyList.ui.CurrencyItemView

/**
 * View model for currency list
 */
interface ICurrencyViewModel {
    /**
     * view state for list currencies
     */
    val viewState: MutableLiveData<List<CurrencyItemView>>

    /**
     * set base currency
     * @param cur - name of currency
     */
    fun setBaseCurrency(cur: String)

    fun startLoading()
    fun stopLoading()

    fun setBaseValue(value: Double)
}