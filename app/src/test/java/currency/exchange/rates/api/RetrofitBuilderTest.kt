package currency.exchange.rates.api

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class RetrofitBuilderTest {

    @Test
    fun `should request currencies and print them`() {
        val latch = CountDownLatch(1)
        RetrofitBuilder()
                .createApi(true)
                .getLatestCurrencies("AUD")
                .subscribe({
                    System.out.println(it)
                    assertEquals(it.rates["AUD"] ?: BigDecimal(0.0), BigDecimal(0.0))
                    latch.countDown()
                }, {
                    it.printStackTrace()
                })

        latch.await(10, TimeUnit.SECONDS)
    }

    @Test
    fun `should return error for currency`() {
        val latch = CountDownLatch(1)
        RetrofitBuilder()
                .createApi(true)
                .getLatestCurrencies("DEDED")
                .subscribe({
                    System.out.println(it)
                    latch.countDown()
                }, {
                    assertEquals("Invalid base", it.message)
                    latch.countDown()
                })

        latch.await(10, TimeUnit.SECONDS)
    }

    @Test
    fun `should request country by currency and print them`() {
        val latch = CountDownLatch(1)
        RetrofitBuilder()
                .createCountryApi(true)
                .getCountriesInfo()
                .subscribe({
                    System.out.println(it)
                    assertEquals("Australia", it[0].name)
                    assertEquals("https://restcountries.eu/data/aus.svg", it[0].flag)
                    assertEquals("AUD", it[0].currencyCode)
                    assertEquals("Australian dollar", it[0].currencyName)
                    latch.countDown()
                }, {
                    it.printStackTrace()
                })

        latch.await(10, TimeUnit.SECONDS)
    }
}