package ru.plamit.currencytest.currencyList.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ru.plamit.currencytest.currencyList.ICurrencyInteractor
import ru.plamit.currencytest.currencyList.ICurrencyViewModel

class CurrencyViewModel(
        private val interactor: ICurrencyInteractor): ViewModel(), ICurrencyViewModel{
    override val viewState: MutableLiveData<List<CurrencyItemView>> = MutableLiveData()

    var repeatDelay = 1000 //repeat loading delay. Public for tests

    override fun setBaseCurrency(cur: String) {
    }

    override fun startLoading() {
    }

    override fun stopLoading() {
    }

    override fun setBaseValue(value: Double) {
    }
}