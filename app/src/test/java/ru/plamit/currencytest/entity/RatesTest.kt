package ru.plamit.currencytest.entity

import com.google.gson.Gson
import org.junit.Assert.*
import org.junit.Test

class RatesTest {

    @Test
    fun `should ignore base currency and not crash`() {
        val json = "{\"base\":\"AUD\",\"date\":\"2018-09-06\",\"rates\":{\"BGN\":1.2128,\"BRL\":2.9713,\"CAD\":0.95106,\"CHF\":0.69915,\"CNY\":4.9266,\"CZK\":15.945,\"DKK\":4.6237,\"GBP\":0.55698,\"HKD\":5.6628,\"HRK\":4.6097,\"HUF\":202.45,\"IDR\":10742.0,\"ILS\":2.5861,\"INR\":51.911,\"ISK\":79.244,\"JPY\":80.333,\"KRW\":809.02,\"MXN\":13.868,\"MYR\":2.9838,\"NOK\":6.0619,\"NZD\":1.0934,\"PHP\":38.812,\"PLN\":2.6777,\"RON\":2.8762,\"RUB\":49.342,\"SEK\":6.5671,\"SGD\":0.99214,\"THB\":23.643,\"TRY\":4.7301,\"USD\":0.72142,\"ZAR\":11.052,\"EUR\":0.62008}}"

        val result = Gson().fromJson<Currency>(json, Currency::class.java)

        assertEquals(result.rates.aud, 0.0, 0.0)
    }

    @Test
    fun `should ignore new currencies than not described`(){
        val json = "{\"base\":\"AUD\",\"date\":\"2018-09-06\",\"rates\":{\"POLSO\":1.0, \"BGN\":1.2128,\"BRL\":2.9713,\"CAD\":0.95106,\"CHF\":0.69915,\"CNY\":4.9266,\"CZK\":15.945,\"DKK\":4.6237,\"GBP\":0.55698,\"HKD\":5.6628,\"HRK\":4.6097,\"HUF\":202.45,\"IDR\":10742.0,\"ILS\":2.5861,\"INR\":51.911,\"ISK\":79.244,\"JPY\":80.333,\"KRW\":809.02,\"MXN\":13.868,\"MYR\":2.9838,\"NOK\":6.0619,\"NZD\":1.0934,\"PHP\":38.812,\"PLN\":2.6777,\"RON\":2.8762,\"RUB\":49.342,\"SEK\":6.5671,\"SGD\":0.99214,\"THB\":23.643,\"TRY\":4.7301,\"USD\":0.72142,\"ZAR\":11.052,\"EUR\":0.62008}}"

        val result = Gson().fromJson<Currency>(json, Currency::class.java)

        assertEquals(result.rates.aud, 0.0, 0.0)
        assertEquals(result.rates.bgn, 1.2128, 0.0)
    }
}