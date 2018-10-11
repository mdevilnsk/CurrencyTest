package ru.plamit.currencytest.currencyList.ui

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import ru.plamit.currencytest.R
import ru.plamit.currencytest.currencyList.ICurrencyInteractor
import ru.plamit.currencytest.currencyList.ICurrencyRouter
import ru.plamit.currencytest.currencyList.ICurrencyViewModel
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class CurrencyViewModel(
        private val interactor: ICurrencyInteractor
) : ViewModel(), ICurrencyViewModel {

    override val viewState: MutableLiveData<List<CurrencyItemView>> = MutableLiveData()
    override val baseView: MutableLiveData<CurrencyItemView> = MutableLiveData()

    var router: ICurrencyRouter? = null

    var repeatDelay = 1000L //repeat loading delay. Public for tests
    private var startLoadingTimer: Timer? = null
    private var baseCurrency = "USD"
    private var koeff = BigDecimal(1)

    @SuppressLint("CheckResult")
    override fun setBaseCurrency(cur: String) {
        baseCurrency = cur
        interactor.getCountriesInfo().subscribe({countries->
            countries.find { countryInfo -> countryInfo.currencyCode == baseCurrency}?.let {
                baseView.postValue(CurrencyItemView(
                        it.flag,
                        baseCurrency,
                        it.currencyName,
                        BigDecimal("1").toDouble(),
                        true
                ))
            }
        },{
            router?.routeToError(R.string.wrong_base_currency)
        })
        koeff = BigDecimal(1)
    }

    override fun startLoading() {
        startLoadingTimer = Timer("startLoading")
        startLoadingTimer?.schedule(0L, repeatDelay) {
            loadQuery()
        }
    }

    override fun stopLoading() {
        startLoadingTimer?.cancel()
        startLoadingTimer?.purge()
        startLoadingTimer = null
    }

    override fun setBaseValue(value: BigDecimal) {
        koeff = value
    }

    @SuppressLint("CheckResult")
    private fun loadQuery() {
        val rateViews: Single<List<CurrencyItemView>> = Single.zip(interactor.getCurrencies(baseCurrency), interactor.getCountriesInfo(), BiFunction { rates, countries ->
            val views: MutableList<CurrencyItemView> = ArrayList()
            for ((currency, rate) in rates.rates) {
                val country = countries.find { it.currencyCode == currency}
                country?.let {
                    views.add(CurrencyItemView(
                            it.flag,
                            currency,
                            it.currencyName,
                            (rate * koeff).toDouble(),
                            false
                    ))
                }
            }
            return@BiFunction views
        })

        rateViews.subscribe({
            viewState.postValue(it)
        }, {
            router?.routeToError(R.string.cant_load)
        })
    }

    override fun onCleared() {
        super.onCleared()
        stopLoading()
    }
}