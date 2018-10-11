package ru.plamit.currencytest.currencyList.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.currency_item.view.*
import org.koin.standalone.KoinComponent
import ru.plamit.currencytest.R

class CurrencyListAdapter : RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder>(), KoinComponent {

    var items: MutableList<CurrencyItemView> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int) =
            CurrencyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.currency_item, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viwHolder: CurrencyViewHolder, position: Int) {
        val item = items[position]
        viwHolder.itemView.apply {
//            glide.load(item.flagUrl).into(currencyFlag)
            currencyName.text = item.name
            currencyDescription.text = item.description
            currencyEditTil.isEnabled = item.base
            currencyRateEt.setText(item.rate.toString())
        }
    }

    class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}