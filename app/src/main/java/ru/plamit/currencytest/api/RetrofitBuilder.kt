package ru.plamit.currencytest.api

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.plamit.currencytest.BuildConfig
import ru.plamit.currencytest.api.ICurrencyApi.Companion.BASE_URL
import java.util.concurrent.TimeUnit

/**
 * Class for create api implementation of interface
 * TODO: add response interceptor
 */
class RetrofitBuilder {

    companion object {
        private const val HTTP_LOG_TAG = "OkHttp"
    }

    fun createApi(test: Boolean = false): ICurrencyApi {
        val httpClient = OkHttpClient.Builder().apply {
            connectTimeout(20, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
            addInterceptor(CurrencyResponseInterceptor())
            if (!test) addInterceptor(LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.BASIC)
                    .log(Platform.INFO)
                    .tag(HTTP_LOG_TAG)
                    .build())
        }.build()

        val retrofitBuilder = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)

        return retrofitBuilder.build().create(ICurrencyApi::class.java)
    }
}