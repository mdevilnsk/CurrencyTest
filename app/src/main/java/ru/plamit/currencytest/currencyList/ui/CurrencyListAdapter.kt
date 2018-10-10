package ru.plamit.currencytest.currencyList.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

class CurrencyListAdapter: RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CurrencyViewHolder {
        TODO()
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(viwHolder: CurrencyViewHolder, position: Int) {
        return
    }

    class CurrencyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}