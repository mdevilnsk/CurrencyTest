package ru.plamit.currencytest

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_currency.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.plamit.currencytest.currencyList.ui.CurrencyListAdapter
import ru.plamit.currencytest.currencyList.ui.CurrencyViewModel


class CurrencyActivity : AppCompatActivity() {

    private val currencyViewModel: CurrencyViewModel by viewModel()

    private val adapter = CurrencyListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)
    }

    override fun onStart() {
        super.onStart()
        currenciesListRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        currenciesListRv.adapter = adapter

        currencyViewModel.viewState.observe(this, Observer { currenciesList ->
            currenciesList?.let { adapter.items = ArrayList(it) }
        })
        currencyViewModel.startLoading()
    }

    override fun onStop() {
        super.onStop()
        currencyViewModel.stopLoading()
    }


}
