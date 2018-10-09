package ru.plamit.currencytest.api

import org.junit.Assert.*
import org.junit.Test
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
                    assertEquals(it.rates["AUD"] ?: 0.0, 0.0, 0.0)
                    latch.countDown()
                }, {
                    it.printStackTrace()
                })

        latch.await(10, TimeUnit.SECONDS)
    }

    @Test
    fun `should return error`() {
        val latch = CountDownLatch(1)
        RetrofitBuilder()
                .createApi(true)
                .getLatestCurrencies("DEDED")
                .subscribe({
                    System.out.println(it)
                    latch.countDown()
                }, {
                    it.printStackTrace()
                })

        latch.await(10, TimeUnit.SECONDS)
    }
}