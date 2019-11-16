package currency.exchange.rates.currencyList.ui

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import kotlinx.android.synthetic.main.activity_currency.*
import kotlinx.android.synthetic.main.currency_item.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import currency.exchange.rates.R
import currency.exchange.rates.currencyList.ICurrencyRouter
import currency.exchange.rates.utils.ErrorMessageDialogFragment
import currency.exchange.rates.utils.addTextWatcher
import currency.exchange.rates.utils.currToDrawable
import currency.exchange.rates.utils.gone
import java.math.BigDecimal


class CurrencyActivity :
        AppCompatActivity(),
        CurrencyListAdapter.ItemSelectionListener,
        ICurrencyRouter {

    private val currencyViewModel: CurrencyViewModel by viewModel()

    private val adapter = CurrencyListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)
        if (savedInstanceState == null) currencyViewModel.setBaseCurrency("USD")
        currencyViewModel.router = this
    }

    override fun onStart() {
        super.onStart()
        currenciesListRv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
        currenciesListRv.adapter = adapter
        adapter.itemSelectionListener = this

        currencyViewModel.viewState.observe(this, Observer { currenciesList ->
            loadingPb.gone()
            currenciesList?.let { adapter.items = ArrayList(it) }
        })

        currencyViewModel.baseView.observe(this, Observer { item ->
            item?.let {
                loadingPb.gone()
                currencyFlag.setImageDrawable(VectorDrawableCompat.create(resources, currToDrawable(item.name), null))
                currencyName.text = item.name
                currencyDescription.text = item.description
                currencyEditTil.isEnabled = item.base
                currencyRateEt.setText(item.rate.toString())
            }
        })
        currencyViewModel.startLoading()
        currencyRateEt.addTextWatcher { s, _, _, count ->
            if (s?.length!! > 0)
                try {
                    val base = s.toString().toDouble()
                    currencyViewModel.setBaseValue(BigDecimal.valueOf(base))
                } catch (e: Throwable) {
                    ErrorMessageDialogFragment.buildDialog(getString(R.string.error), getString(R.string.wrong_number), click = {
                        currencyRateEt.setText("1")
                    }).show(supportFragmentManager, "errorDialog")
                }
        }
    }

    override fun onStop() {
        super.onStop()
        currencyViewModel.stopLoading()
    }

    override fun onItemSelected(currency: String) {
        currencyViewModel.setBaseCurrency(currency)
    }

    override fun routeToError(@StringRes message: Int) {
        currencyViewModel.stopLoading()
        ErrorMessageDialogFragment.buildDialog(getString(R.string.error), getString(message), click = {
            it.dismiss()
            currencyViewModel.startLoading()
        }).show(supportFragmentManager, "errorDialog")
    }
}
