package ru.plamit.currencytest.currencyList.ui

import java.math.BigDecimal

data class CurrencyItemView(val flagUrl: String, val name: String, val description: String, val rate: Double, val base: Boolean)