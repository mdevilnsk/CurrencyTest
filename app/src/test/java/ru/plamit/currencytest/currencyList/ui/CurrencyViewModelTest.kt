package ru.plamit.currencytest.currencyList.ui

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.google.gson.internal.LinkedTreeMap
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import ru.plamit.currencytest.currencyList.ICurrencyInteractor
import ru.plamit.currencytest.entity.CountryInfo
import ru.plamit.currencytest.entity.CurrenciesItem
import ru.plamit.currencytest.entity.CurrencyRates
import java.math.BigDecimal

@Suppress("NonAsciiCharacters", "UNCHECKED_CAST")
@RunWith(MockitoJUnitRunner::class)
class CurrencyViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CurrencyViewModel

    private val interactor = mock(ICurrencyInteractor::class.java)

    private val viewStateObserver = mock(Observer::class.java
            , Mockito.withSettings().verboseLogging()
    ) as Observer<List<CurrencyItemView>>

    @Before
    fun setUp() {
        `when`(interactor.getCountriesInfo()).thenReturn(Single.just(arrayListOf(
                generateCountryInfo("USD"),
                generateCountryInfo("RUB"),
                generateCountryInfo("EUR")
        )))
        viewModel = CurrencyViewModel(interactor)
        viewModel.repeatDelay = 200
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(interactor)
    }

    @Test fun `should start request and repeat every (1) second`(){
        //precondition
        `when`(interactor.getCurrencies(anyString())).thenReturn(Single.just(generateCurrency("USD")))


        //action
        viewModel.viewState.observeForever(viewStateObserver)
        viewModel.startLoading()

        //result
        Thread.sleep(300)
        verify(interactor, times(2)).getCountriesInfo()
        verify(interactor, times(2)).getCurrencies("USD")
        verify(viewStateObserver, times(2)).onChanged(arrayListOf(
                CurrencyItemView("flag_USD", "USD", "country_USD",BigDecimal(0.0).toDouble(),true),
                CurrencyItemView("flag_RUB", "RUB", "country_RUB",BigDecimal(65.0).toDouble(),false),
                CurrencyItemView("flag_EUR", "EUR", "country_EUR",BigDecimal(0.86).toDouble(),false)
        ))
    }

    @Test fun `should stop requests`(){
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
                CurrencyItemView("flag_USD", "USD", "country_USD",BigDecimal(0.0).toDouble(),true),
                CurrencyItemView("flag_RUB", "RUB", "country_RUB",BigDecimal(65.0).toDouble(),false),
                CurrencyItemView("flag_EUR", "EUR", "country_EUR",BigDecimal(0.86).toDouble(),false)
        ))
    }

    @Test fun `should set different base currency`(){

        val interactorInOrder = inOrder(interactor)
        val viewInOrder = inOrder(viewStateObserver)
        //precondition
        `when`(interactor.getCurrencies(anyString()))
                .thenReturn(Single.just(generateCurrency("USD")))
                .thenReturn(Single.just(generateCurrency("RUB")))

        //action
        viewModel.viewState.observeForever(viewStateObserver)
        viewModel.startLoading()
        Thread.sleep(100)
        viewModel.setBaseCurrency("RUB")

        //result
        Thread.sleep(300)
        interactorInOrder.verify(interactor).getCurrencies("USD")
        interactorInOrder.verify(interactor).getCountriesInfo()
        interactorInOrder.verify(interactor).getCurrencies("RUB")
        interactorInOrder.verify(interactor).getCountriesInfo()
        viewInOrder.verify(viewStateObserver).onChanged(arrayListOf(
                CurrencyItemView("flag_USD", "USD", "country_USD",BigDecimal(0.0).toDouble(),true),
                CurrencyItemView("flag_RUB", "RUB", "country_RUB",BigDecimal(65.0).toDouble(),false),
                CurrencyItemView("flag_EUR", "EUR", "country_EUR",BigDecimal(0.86).toDouble(),false)
        ))
        viewInOrder.verify(viewStateObserver).onChanged(arrayListOf(
                CurrencyItemView("flag_RUB", "RUB", "country_RUB",BigDecimal(0.0).toDouble(),true),
                CurrencyItemView("flag_USD", "USD", "country_USD",BigDecimal(0.015).toDouble(),false),
                CurrencyItemView("flag_EUR", "EUR", "country_EUR",BigDecimal(0.013).toDouble(),false)
        ))
    }
//    @Test fun `should`(){}
//    @Test fun `should`(){}
//    @Test fun `should`(){}

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
            arrayListOf(CurrenciesItem("", currCode,"cur_$currCode"))
    )
}