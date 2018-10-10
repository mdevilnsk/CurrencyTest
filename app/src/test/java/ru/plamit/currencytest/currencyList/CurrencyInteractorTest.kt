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
    fun `should return information about country and cache value`() {
        //precondition
        `when`(countryApi.getCountryInfoByCurrency(anyString()))
                .thenReturn(Single.just(arrayListOf(CountryInfo("flag", "name"))))
        //action
        val observer = interactor.getCountryInfoByCurrency("CUR").test()
        Thread.sleep(20)
        val observer2 = interactor.getCountryInfoByCurrency("CUR").test()
        //result
        verify(countryApi).getCountryInfoByCurrency("CUR")
        observer.assertValue(CountryInfo("flag", "name", "CUR"))
        observer2.assertValue(CountryInfo("flag", "name", "CUR"))
    }

    @Test
    fun `should return information about different country`() {
        //precondition
        `when`(countryApi.getCountryInfoByCurrency(anyString()))
                .thenReturn(Single.just(arrayListOf(CountryInfo("flag", "name"))))
                .thenReturn(Single.just(arrayListOf(CountryInfo("galf", "eman"))))
        //action
        val observer = interactor.getCountryInfoByCurrency("CUR").test()
        val observer2 = interactor.getCountryInfoByCurrency("RUC").test()
        //result
        verify(countryApi).getCountryInfoByCurrency("CUR")
        verify(countryApi).getCountryInfoByCurrency("RUC")
        observer.assertValue(CountryInfo("flag", "name", "CUR"))
        observer2.assertValue(CountryInfo("galf", "eman", "RUC"))
    }

    @Test
    fun `should return error for currency`() {
        //precondition
        val error = Throwable("error")
        `when`(countryApi.getCountryInfoByCurrency(anyString()))
                .thenReturn(Single.error(error))
        //action
        val observer = interactor.getCountryInfoByCurrency("CUR").test()
        //result
        verify(countryApi).getCountryInfoByCurrency("CUR")
        observer.assertError(error)
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
}