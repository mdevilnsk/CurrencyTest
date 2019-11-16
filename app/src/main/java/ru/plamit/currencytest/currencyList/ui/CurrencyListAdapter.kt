package ru.plamit.currencytest.currencyList.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import kotlinx.android.synthetic.main.currency_item.view.*
import org.koin.core.KoinComponent
import ru.plamit.currencytest.R
import ru.plamit.currencytest.utils.currToDrawable
import ru.plamit.currencytest.utils.onClick

class CurrencyListAdapter : RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder>(), KoinComponent {

    var itemSelectionListener: ItemSelectionListener? = null

    var items: MutableList<CurrencyItemView> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface ItemSelectionListener {
        fun onItemSelected(currency: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int) =
            CurrencyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.currency_item, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viwHolder: CurrencyViewHolder, position: Int) {
        val item = items[position]
        viwHolder.itemView.onClick {
            itemSelectionListener?.onItemSelected(item.name)
        }
        viwHolder.itemView.apply {
            currencyFlag.setImageDrawable(VectorDrawableCompat.create(resources, currToDrawable(item.name), null))
            currencyName.text = item.name
            currencyDescription.text = item.description
            currencyEditTil.isEnabled = item.base
            currencyRateEt.setText(item.rate.toString())
        }
    }

    class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}