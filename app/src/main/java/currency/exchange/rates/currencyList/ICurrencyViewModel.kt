package currency.exchange.rates.currencyList

import androidx.lifecycle.MutableLiveData
import currency.exchange.rates.currencyList.ui.CurrencyItemView
import java.math.BigDecimal

/**
 * View model for currency list
 */
interface ICurrencyViewModel {
    /**
     * view state for list currencies
     */
    val viewState: MutableLiveData<List<CurrencyItemView>>
    val baseView: MutableLiveData<CurrencyItemView>

    /**
     * set base currency
     * @param cur - name of currency
     */
    fun setBaseCurrency(cur: String)

    /**
     * start loading currencies rates
     */
    fun startLoading()

    /**
     * stop loading currencies rates
     */
    fun stopLoading()

    /**
     * set current currency value
     */
    fun setBaseValue(value: BigDecimal)
}