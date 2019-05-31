package com.github.zmcgugin

import com.github.zmcgugin.execution.StockService
import com.github.zmcgugin.finviz.services.FinvizScraper
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

fun main(args: Array<String>) {

    val kodein = Kodein {
        bind<FinvizScraper>() with singleton { FinvizScraper() }
        bind<StockService>() with singleton { StockService(kodein) }
    }

    StockService(kodein).findValueStocks()
}