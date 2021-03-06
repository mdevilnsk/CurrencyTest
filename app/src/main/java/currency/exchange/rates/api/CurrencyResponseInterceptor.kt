package currency.exchange.rates.api

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.RuntimeException
import java.net.ConnectException
import java.net.UnknownHostException

class CurrencyResponseInterceptor : Interceptor{

        override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response
        try {
            response = chain.proceed(chain.request())
        } catch (e: UnknownHostException) {
            throw RuntimeException("check internet connection")
        } catch (e: ConnectException) {
            throw RuntimeException("can't connect to server")
        }

        when (response.code()) {
            in 200..299 -> return response
            else -> {
                val baseResponse = Gson().fromJson(response.body()!!.string(), BaseErrorResponse::class.java)
                val e = RuntimeException(baseResponse.error ?: baseResponse.message ?: "unknown error")
                throw e
            }
        }
    }
}