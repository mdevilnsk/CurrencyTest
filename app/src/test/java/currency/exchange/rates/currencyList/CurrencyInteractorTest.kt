package currency.exchange.rates.currencyList

import com.google.gson.internal.LinkedTreeMap
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import currency.exchange.rates.api.ICurrencyApi
import currency.exchange.rates.entity.CountryInfo
import currency.exchange.rates.entity.CurrencyRates
import currency.exchange.rates.utils.DefaultSchedulerTest
import java.math.BigDecimal

@Suppress("NonAsciiCharacters", "UNCHECKED_CAST")
@RunWith(MockitoJUnitRunner::class)
class CurrencyInteractorTest {

    private lateinit var interactor: CurrencyInteractor

    private val api = mock(ICurrencyApi::class.java)
    private val testScheduler = DefaultSchedulerTest()

    @Before
    fun setUp() {
        interactor = CurrencyInteractor(api, testScheduler)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(api)
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
        //action
        val observer = interactor.getCountriesInfo().test()
        Thread.sleep(20)
        val observer2 = interactor.getCountriesInfo().test()
        //result
        observer.assertValue(countries)
        observer2.assertValue(countries)
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