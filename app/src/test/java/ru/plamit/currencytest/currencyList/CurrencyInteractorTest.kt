package ru.plamit.currencytest.currencyList

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.internal.LinkedTreeMap
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import ru.plamit.currencytest.api.ICurrencyApi
import ru.plamit.currencytest.entity.CurrencyRates

@Suppress("NonAsciiCharacters", "UNCHECKED_CAST")
@RunWith(MockitoJUnitRunner::class)
class CurrencyInteractorTest {

    @get:Rule
    var rule: TestRule =

            InstantTaskExecutorRule()

    private lateinit var interactor: CurrencyInteractor

    val api = mock(ICurrencyApi::class.java)

    @Before
    fun setUp() {
        interactor = CurrencyInteractor(api)
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

    fun generateCurrency(base: String): CurrencyRates {

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