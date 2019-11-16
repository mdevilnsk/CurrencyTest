package currency.exchange.rates.currencyList.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.gson.internal.LinkedTreeMap
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import currency.exchange.rates.currencyList.ICurrencyInteractor
import currency.exchange.rates.entity.CountryInfo
import currency.exchange.rates.entity.CurrencyRates
import java.math.BigDecimal

@Suppress("NonAsciiCharacters", "UNCHECKED_CAST")
@RunWith(MockitoJUnitRunner::class)
class CurrencyViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    companion object {
        private const val LOAD_DELAY = 210L
    }

    private lateinit var viewModel: CurrencyViewModel
    private val interactor = mock(ICurrencyInteractor::class.java)
    private val viewStateObserver = mock(Observer::class.java
//            , Mockito.withSettings().verboseLogging()
    ) as Observer<List<CurrencyItemView>>
    private val baseViewObserver = mock(Observer::class.java
//            , Mockito.withSettings().verboseLogging()
    ) as Observer<CurrencyItemView>

    @Before
    fun setUp() {
        `when`(interactor.getCountriesInfo()).thenReturn(Single.just(arrayListOf(
                generateCountryInfo("USD"),
                generateCountryInfo("RUB"),
                generateCountryInfo("EUR")
        )))
        viewModel = CurrencyViewModel(interactor)
        viewModel.repeatDelay = LOAD_DELAY
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(interactor)
        verifyNoMoreInteractions(viewStateObserver)
        verifyNoMoreInteractions(baseViewObserver)
    }

    @Test
    fun `should start request and repeat every (1) second`() {
        //precondition
        `when`(interactor.getCurrencies(anyString())).thenReturn(Single.just(generateCurrency("USD")))

        //action
        viewModel.viewState.observeForever(viewStateObserver)
        viewModel.startLoading()

        //result
        Thread.sleep(LOAD_DELAY + LOAD_DELAY - 10)
        verify(interactor, times(2)).getCountriesInfo()
        verify(interactor, times(2)).getCurrencies("USD")
        verify(viewStateObserver, times(2)).onChanged(arrayListOf(
                CurrencyItemView("flag_RUB", "RUB", "name_RUB", BigDecimal(65.0).toDouble(), false),
                CurrencyItemView("flag_EUR", "EUR", "name_EUR", BigDecimal(0.86).toDouble(), false)
        ))
    }

    @Test
    fun `should stop requests`() {
        //precondition
        `when`(interactor.getCurrencies(anyString())).thenReturn(Single.just(generateCurrency("USD")))

        //action
        viewModel.viewState.observeForever(viewStateObserver)
        viewModel.startLoading()
        Thread.sleep(20)
        viewModel.stopLoading()
        //result
        Thread.sleep(300)

        verify(interactor).getCountriesInfo()
        verify(interactor).getCurrencies("USD")
        verify(viewStateObserver).onChanged(arrayListOf(
                CurrencyItemView("flag_RUB", "RUB", "name_RUB", BigDecimal(65.0).toDouble(), false),
                CurrencyItemView("flag_EUR", "EUR", "name_EUR", BigDecimal(0.86).toDouble(), false)
        ))
    }

    @Test
    fun `should set different base currency`() {

        val interactorInOrder = inOrder(interactor)
        val viewInOrder = inOrder(viewStateObserver)
        //precondition
        `when`(interactor.getCurrencies(anyString()))
                .thenReturn(Single.just(generateCurrency("USD")))
                .thenReturn(Single.just(generateCurrency("RUB")))

        //action
        viewModel.baseView.observeForever(baseViewObserver)
        viewModel.viewState.observeForever(viewStateObserver)
        viewModel.startLoading()
        Thread.sleep(50)
        viewModel.setBaseCurrency("RUB")

        //result
        Thread.sleep(LOAD_DELAY * 1.5.toLong())

        interactorInOrder.verify(interactor).getCurrencies("USD")
        verify(interactor, times(3)).getCountriesInfo()
        interactorInOrder.verify(interactor).getCurrencies("RUB")

        verify(baseViewObserver).onChanged(CurrencyItemView("flag_RUB", "RUB", "name_RUB", BigDecimal("1.0").toDouble(), true))

        viewInOrder.verify(viewStateObserver).onChanged(arrayListOf(
                CurrencyItemView("flag_RUB", "RUB", "name_RUB", BigDecimal(65.0).toDouble(), false),
                CurrencyItemView("flag_EUR", "EUR", "name_EUR", BigDecimal(0.86).toDouble(), false)
        ))

        viewInOrder.verify(viewStateObserver).onChanged(arrayListOf(
                CurrencyItemView("flag_USD", "USD", "name_USD", BigDecimal(0.015).toDouble(), false),
                CurrencyItemView("flag_EUR", "EUR", "name_EUR", BigDecimal(0.013).toDouble(), false)
        ))
    }

    @Test
    fun `should set currency rate and multiply for value`() {
        val inOrder = inOrder(viewStateObserver)

        //precondition
        `when`(interactor.getCurrencies(anyString())).thenReturn(Single.just(generateCurrency("USD")))

        //action
        viewModel.viewState.observeForever(viewStateObserver)
        viewModel.startLoading()
        Thread.sleep(50)
        viewModel.setBaseValue(BigDecimal(2))

        //result
        Thread.sleep(LOAD_DELAY * 1.5.toLong())
        verify(interactor, times(2)).getCountriesInfo()
        verify(interactor, times(2)).getCurrencies("USD")
        inOrder.verify(viewStateObserver).onChanged(arrayListOf(
                CurrencyItemView("flag_RUB", "RUB", "name_RUB", BigDecimal(65.0).toDouble(), false),
                CurrencyItemView("flag_EUR", "EUR", "name_EUR", BigDecimal(0.86).toDouble(), false)
        ))


        inOrder.verify(viewStateObserver).onChanged(arrayListOf(
                CurrencyItemView("flag_RUB", "RUB", "name_RUB", BigDecimal(130.0).toDouble(), false),
                CurrencyItemView("flag_EUR", "EUR", "name_EUR", BigDecimal(1.72).toDouble(), false)
        ))
    }

    private fun generateCurrency(base: String): CurrencyRates {

        val currencies = LinkedTreeMap<String, BigDecimal>()
        when (base) {
            "RUB" -> {
                currencies["USD"] = BigDecimal(0.015); currencies["EUR"] = BigDecimal(0.013)
            }
            "USD" -> {
                currencies["RUB"] = BigDecimal(65.0); currencies["EUR"] = BigDecimal(0.86)
            }
            "EUR" -> {
                currencies["RUB"] = BigDecimal(75.0); currencies["USD"] = BigDecimal(1.15)
            }
            else -> {
                currencies["RUB"] = BigDecimal(1.0); currencies["USD"] = BigDecimal(0.015); currencies["EUR"] = BigDecimal(0.013)
            }
        }
        return CurrencyRates("date", currencies, base)
    }

    private fun generateCountryInfo(currCode: String) = CountryInfo(
            "flag_$currCode",
            "country_$currCode",
            currCode,
            "name_$currCode"
    )
}