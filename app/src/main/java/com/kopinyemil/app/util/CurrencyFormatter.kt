package com.kopinyemil.app.util

import java.text.NumberFormat
import java.util.Locale

private val rupiahFormat: NumberFormat = NumberFormat.getInstance(Locale("id", "ID"))

fun Int.toRupiah(): String = "Rp" + rupiahFormat.format(this)
