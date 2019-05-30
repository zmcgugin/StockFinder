package com.github.zmcgugin.finviz.objects

data class FinvizRow(
    var ticker: String,
    var company: String,
    var sector: String,
    var industry: String,
    var country: String,
    var marketCap: String,
    var pE: Double,
    var price: Double,
    var change: Double,
    var volume: Double
)