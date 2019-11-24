package currency.exchange.rates.currencyList

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import currency.exchange.rates.api.ICurrencyApi
import currency.exchange.rates.entity.CountryInfo
import currency.exchange.rates.entity.CurrencyRates
import currency.exchange.rates.utils.IDefaultScheduler
import io.reactivex.Single

class CurrencyInteractor(
        private val api: ICurrencyApi,
        private val scheduler: IDefaultScheduler
) : ICurrencyInteractor {

    private var currencyRates: CurrencyRates? = null

    override fun getCurrencies(baseCurrency: String, force: Boolean): Single<CurrencyRates> {
        if (currencyRates == null || force) {
            return api.getLatestCurrencies(baseCurrency)
                    .map {
                        currencyRates = it
                        it
                    }
                    .compose(scheduler.applySingle())
        } else {
            return Single.just(currencyRates)
        }
    }

    override fun getCountriesInfo(): Single<List<CountryInfo?>> {
        //region hardcode
        val countriesJson = "[\n" +
                "  {\n" +
                "    \"id\": \"1\",\n" +
                "    \"name\": \"Australia\",\n" +
                "    \"cur_code\": \"AUD\",\n" +
                "    \"cur_name\": \"Australian dollar\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"17\",\n" +
                "    \"name\": \"Iceland\",\n" +
                "    \"cur_code\": \"ISK\",\n" +
                "    \"cur_name\": \"Icelandic kr\\u00f3na\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"3\",\n" +
                "    \"name\": \"Bulgaria\",\n" +
                "    \"cur_code\": \"BGN\",\n" +
                "    \"cur_name\": \"Bulgarian lev\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"4\",\n" +
                "    \"name\": \"Brazil\",\n" +
                "    \"cur_code\": \"BRL\",\n" +
                "    \"cur_name\": \"Brazilian real\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"5\",\n" +
                "    \"name\": \"Canada\",\n" +
                "    \"cur_code\": \"CAD\",\n" +
                "    \"cur_name\": \"Canadian dollar\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"6\",\n" +
                "    \"name\": \"Switzerland\",\n" +
                "    \"cur_code\": \"CHF\",\n" +
                "    \"cur_name\": \"Swiss franc\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"7\",\n" +
                "    \"name\": \"China\",\n" +
                "    \"cur_code\": \"CNY\",\n" +
                "    \"cur_name\": \"Chinese yuan\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"8\",\n" +
                "    \"name\": \"Czech Republic\",\n" +
                "    \"cur_code\": \"CZK\",\n" +
                "    \"cur_name\": \"Czech koruna\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"9\",\n" +
                "    \"name\": \"Denmark\",\n" +
                "    \"cur_code\": \"DKK\",\n" +
                "    \"cur_name\": \"Danish krone\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"10\",\n" +
                "    \"name\": \"United Kingdom of Great Britain and Northern Ireland\",\n" +
                "    \"cur_code\": \"GPB\",\n" +
                "    \"cur_name\": \"British pound\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"11\",\n" +
                "    \"name\": \"Hong Kong\",\n" +
                "    \"cur_code\": \"HKD\",\n" +
                "    \"cur_name\": \"Hong Kong dollar\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"12\",\n" +
                "    \"name\": \"Croatia\",\n" +
                "    \"cur_code\": \"HRK\",\n" +
                "    \"cur_name\": \"Croatian kuna\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"13\",\n" +
                "    \"name\": \"Hungary\",\n" +
                "    \"cur_code\": \"HUF\",\n" +
                "    \"cur_name\": \"Hungarian forint\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"14\",\n" +
                "    \"name\": \"Indonesia\",\n" +
                "    \"cur_code\": \"IDR\",\n" +
                "    \"cur_name\": \"Indonesian rupiah\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"15\",\n" +
                "    \"name\": \"Israel\",\n" +
                "    \"cur_code\": \"ILS\",\n" +
                "    \"cur_name\": \"Israeli new shekel\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"16\",\n" +
                "    \"name\": \"India\",\n" +
                "    \"cur_code\": \"INR\",\n" +
                "    \"cur_name\": \"Indian rupee\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"18\",\n" +
                "    \"name\": \"Japan\",\n" +
                "    \"cur_code\": \"JPY\",\n" +
                "    \"cur_name\": \"Japanese yen\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"19\",\n" +
                "    \"name\": \"Korea (Republic of)\",\n" +
                "    \"cur_code\": \"KRW\",\n" +
                "    \"cur_name\": \"South Korean won\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"20\",\n" +
                "    \"name\": \"Mexico\",\n" +
                "    \"cur_code\": \"MXN\",\n" +
                "    \"cur_name\": \"Mexican peso\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"21\",\n" +
                "    \"name\": \"Malaysia\",\n" +
                "    \"cur_code\": \"MYR\",\n" +
                "    \"cur_name\": \"Malaysian ringgit\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"22\",\n" +
                "    \"name\": \"Norway\",\n" +
                "    \"cur_code\": \"NOK\",\n" +
                "    \"cur_name\": \"Norwegian krone\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"23\",\n" +
                "    \"name\": \"New Zealand\",\n" +
                "    \"cur_code\": \"NZD\",\n" +
                "    \"cur_name\": \"New Zealand dollar\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"24\",\n" +
                "    \"name\": \"Philippines\",\n" +
                "    \"cur_code\": \"PHP\",\n" +
                "    \"cur_name\": \"Philippine peso\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"25\",\n" +
                "    \"name\": \"Poland\",\n" +
                "    \"cur_code\": \"PLN\",\n" +
                "    \"cur_name\": \"Polish z\\u0142oty\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"26\",\n" +
                "    \"name\": \"Romania\",\n" +
                "    \"cur_code\": \"RON\",\n" +
                "    \"cur_name\": \"Romanian leu\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"27\",\n" +
                "    \"name\": \"Russian Federation\",\n" +
                "    \"cur_code\": \"RUB\",\n" +
                "    \"cur_name\": \"Russian ruble\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"28\",\n" +
                "    \"name\": \"Sweden\",\n" +
                "    \"cur_code\": \"SEK\",\n" +
                "    \"cur_name\": \"Swedish krona\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"29\",\n" +
                "    \"name\": \"Singapore\",\n" +
                "    \"cur_code\": \"SGD\",\n" +
                "    \"cur_name\": \"Singapore dollar\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"30\",\n" +
                "    \"name\": \"Thailand\",\n" +
                "    \"cur_code\": \"THB\",\n" +
                "    \"cur_name\": \"Thai baht\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"31\",\n" +
                "    \"name\": \"Turkey\",\n" +
                "    \"cur_code\": \"TRY\",\n" +
                "    \"cur_name\": \"Turkish lira\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"32\",\n" +
                "    \"name\": \"Zimbabwe\",\n" +
                "    \"cur_code\": \"ZAR\",\n" +
                "    \"cur_name\": \"South African rand\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"33\",\n" +
                "    \"name\": \"Spain\",\n" +
                "    \"cur_code\": \"EUR\",\n" +
                "    \"cur_name\": \"Euro\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"34\",\n" +
                "    \"name\": \"United States of America\",\n" +
                "    \"cur_code\": \"USD\",\n" +
                "    \"cur_name\": \"United States dollar\"\n" +
                "  }\n" +
                "]"
        //endregion
        val type = object : TypeToken<List<CountryInfo>>() {}.type
        return Single.just(Gson().fromJson<List<CountryInfo>>(countriesJson, type))
    }
}