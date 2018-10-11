package ru.plamit.currencytest.entity

import com.google.gson.Gson
import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal

class RatesTest {

    @Test
    fun `should ignore base currency and not crash`() {
        val json = "{\"base\":\"AUD\",\"date\":\"2018-09-06\",\"rates\":{\"BGN\":1.2128,\"BRL\":2.9713,\"CAD\":0.95106,\"CHF\":0.69915,\"CNY\":4.9266,\"CZK\":15.945,\"DKK\":4.6237,\"GBP\":0.55698,\"HKD\":5.6628,\"HRK\":4.6097,\"HUF\":202.45,\"IDR\":10742.0,\"ILS\":2.5861,\"INR\":51.911,\"ISK\":79.244,\"JPY\":80.333,\"KRW\":809.02,\"MXN\":13.868,\"MYR\":2.9838,\"NOK\":6.0619,\"NZD\":1.0934,\"PHP\":38.812,\"PLN\":2.6777,\"RON\":2.8762,\"RUB\":49.342,\"SEK\":6.5671,\"SGD\":0.99214,\"THB\":23.643,\"TRY\":4.7301,\"USD\":0.72142,\"ZAR\":11.052,\"EUR\":0.62008}}"

        val result = Gson().fromJson<CurrencyRates>(json, CurrencyRates::class.java)
        assertEquals(BigDecimal(0).setScale(0, BigDecimal.ROUND_HALF_DOWN), result.rates["AUD"] ?: BigDecimal(0.0))
        assertEquals(BigDecimal(49.342).setScale(3, BigDecimal.ROUND_HALF_DOWN), result.rates["RUB"] ?: BigDecimal(1.0))
    }

    @Test
    fun `should process new currencies`() {
        val json = "{\"base\":\"AUD\",\"date\":\"2018-09-06\",\"rates\":{\"POLSO\":1.5, \"BGN\":1.2128,\"BRL\":2.9713,\"CAD\":0.95106,\"CHF\":0.69915,\"CNY\":4.9266,\"CZK\":15.945,\"DKK\":4.6237,\"GBP\":0.55698,\"HKD\":5.6628,\"HRK\":4.6097,\"HUF\":202.45,\"IDR\":10742.0,\"ILS\":2.5861,\"INR\":51.911,\"ISK\":79.244,\"JPY\":80.333,\"KRW\":809.02,\"MXN\":13.868,\"MYR\":2.9838,\"NOK\":6.0619,\"NZD\":1.0934,\"PHP\":38.812,\"PLN\":2.6777,\"RON\":2.8762,\"RUB\":49.342,\"SEK\":6.5671,\"SGD\":0.99214,\"THB\":23.643,\"TRY\":4.7301,\"USD\":0.72142,\"ZAR\":11.052,\"EUR\":0.62008}}"

        val result = Gson().fromJson<CurrencyRates>(json, CurrencyRates::class.java)

        assertEquals(BigDecimal(1.2128).setScale(4, BigDecimal.ROUND_HALF_DOWN), result.rates["BGN"] ?: BigDecimal(0.0))
        assertEquals(BigDecimal(1.5).setScale(1, BigDecimal.ROUND_HALF_DOWN), result.rates["POLSO"] ?: BigDecimal(0.0))
    }
}