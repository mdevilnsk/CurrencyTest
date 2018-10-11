package ru.plamit.currencytest.currencyList.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_currency.*
import kotlinx.android.synthetic.main.currency_item.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.plamit.currencytest.R
import ru.plamit.currencytest.utils.currToDrawable


class CurrencyActivity : AppCompatActivity(), CurrencyListAdapter.ItemSelectionListener {

    private val currencyViewModel: CurrencyViewModel by viewModel()

    private val adapter = CurrencyListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)
        if (savedInstanceState == null) currencyViewModel.setBaseCurrency("USD")
    }

    override fun onStart() {
        super.onStart()
        currenciesListRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        currenciesListRv.adapter = adapter
        adapter.itemSelectionListener = this

        currencyViewModel.viewState.observe(this, Observer { currenciesList ->
            currenciesList?.let { adapter.items = ArrayList(it) }
        })

        currencyViewModel.baseView.observe(this, Observer { item ->
            item?.let {
                currencyFlag.setImageDrawable(VectorDrawableCompat.create(resources, currToDrawable(item.name), null))
                currencyName.text = item.name
                currencyDescription.text = item.description
                currencyEditTil.isEnabled = item.base
                currencyRateEt.setText(item.rate.toString())
            }
        })
        currencyViewModel.startLoading()
    }

    override fun onStop() {
        super.onStop()
        currencyViewModel.stopLoading()
    }

    override fun onItemSelected(currency: String) {
        currencyViewModel.setBaseCurrency(currency)
    }
}
