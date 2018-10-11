package ru.plamit.currencytest.currencyList

import com.google.gson.internal.LinkedTreeMap
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import ru.plamit.currencytest.api.ICountryApi
import ru.plamit.currencytest.api.ICurrencyApi
import ru.plamit.currencytest.entity.CountryInfo
import ru.plamit.currencytest.entity.CurrenciesItem
import ru.plamit.currencytest.entity.CurrencyRates
import ru.plamit.currencytest.utils.DefaultSchedulerTest

@Suppress("NonAsciiCharacters", "UNCHECKED_CAST")
@RunWith(MockitoJUnitRunner::class)
class CurrencyInteractorTest {

    private lateinit var interactor: CurrencyInteractor

    private val api = mock(ICurrencyApi::class.java)
    private val countryApi = mock(ICountryApi::class.java)
    private val testScheduler = DefaultSchedulerTest()

    @Before
    fun setUp() {
        interactor = CurrencyInteractor(api, countryApi, testScheduler)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(api)
        verifyNoMoreInteractions(countryApi)
    }

    @Test
    fun `should request list of currencies`() {
        //precondition
        val base = "RUB"
        `when`(api.getLatestCurrencies(anyString())).thenReturn(Single.just(generateCurrency(base)))
        //action
        val testObserver = interactor.getCurrencies(base).test()
        //result
        verify(api).getLatestCurrencies(base)
        testObserver.assertValue(generateCurrency(base))
        testObserver.assertNoErrors()
        testObserver.assertComplete()
    }

    @Test
    fun `should return error`() {
        //precondition
        val base = "RUB"
        val error = Throwable("error")
        `when`(api.getLatestCurrencies(anyString())).thenReturn(Single.error(error))
        //action
        val testObserver = interactor.getCurrencies(base).test()
        //result
        verify(api).getLatestCurrencies(base)
        testObserver.assertError(error)
    }

    @Test
    fun `should return information about countries and cache value`() {
        //precondition
        val countries = arrayListOf(
                generateCountryInfo("USD"),
                generateCountryInfo("RUB"),
                generateCountryInfo("EUR")
        )
        `when`(countryApi.getCountriesInfo())
                .thenReturn(Single.just(countries))
        //action
        val observer = interactor.getCountriesInfo().test()
        Thread.sleep(20)
        val observer2 = interactor.getCountriesInfo().test()
        //result
        verify(countryApi).getCountriesInfo()
        observer.assertValue(countries)
        observer2.assertValue(countries)
    }

    private fun generateCurrency(base: String): CurrencyRates {

        val currencies = LinkedTreeMap<String, Double>()
        when (base) {
            "RUB" -> {
                currencies["USD"] = 0.015; currencies["EUR"] = 0.013
            }
            "USD" -> {
                currencies["RUB"] = 65.0; currencies["EUR"] = 0.86
            }
            "EUR" -> {
                currencies["RUB"] = 75.0; currencies["USD"] = 1.15
            }
            else -> {
                currencies["RUB"] = 1.0; currencies["USD"] = 0.015; currencies["EUR"] = 0.013
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