package currency.exchange.rates.api

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import currency.exchange.rates.BuildConfig
import currency.exchange.rates.api.ICountryApi.Companion.COUNTRY_URL
import currency.exchange.rates.api.ICurrencyApi.Companion.BASE_URL
import java.util.concurrent.TimeUnit

/**
 * Class for create api implementation of interface
 */
class RetrofitBuilder {

    companion object {
        private const val HTTP_LOG_TAG = "OkHttp"
        private const val DEFAULT_TIMEOUT = 20L
    }

    fun createApi(test: Boolean = false): ICurrencyApi {
        val httpClient = buildOkHttpClient(DEFAULT_TIMEOUT, test)

        val retrofitBuilder = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)

        return retrofitBuilder.build().create(ICurrencyApi::class.java)
    }

    fun createCountryApi(test: Boolean = false): ICountryApi {
        val httpClient = buildOkHttpClient(DEFAULT_TIMEOUT, test)
        val retrofitBuilder = Retrofit.Builder()
                .baseUrl(COUNTRY_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)

        return retrofitBuilder.build().create(ICountryApi::class.java)
    }

    private fun buildOkHttpClient(timeout: Long, test: Boolean): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(timeout, TimeUnit.SECONDS)
        readTimeout(timeout, TimeUnit.SECONDS)
        addInterceptor(CurrencyResponseInterceptor())
        if (BuildConfig.DEBUG) {
            addInterceptor(
                    LoggingInterceptor.Builder()
                            .loggable(BuildConfig.DEBUG)
                            .setLevel(Level.BODY)
                            .log(Platform.INFO)
                            .tag(HTTP_LOG_TAG)
                            .build()
            )
        }
    }.build()
}