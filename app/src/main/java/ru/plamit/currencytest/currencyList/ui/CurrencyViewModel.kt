package ru.plamit.currencytest.currencyList.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ru.plamit.currencytest.currencyList.ICurrencyViewModel

class CurrencyViewModel: ViewModel(), ICurrencyViewModel{
    override val viewState: MutableLiveData<List<CurrencyItemView>> = MutableLiveData()

    override fun setBaseCurrency(cur: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun startLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stopLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setBaseValue(value: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}