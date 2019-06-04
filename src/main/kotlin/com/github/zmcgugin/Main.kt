package com.github.zmcgugin

import com.github.zmcgugin.calculators.FinancialCalculator
import com.github.zmcgugin.execution.StockService
import com.github.zmcgugin.finviz.services.FinvizScraper
import com.github.zmcgugin.marketwatch.services.MarketwatchScraper
import com.github.zmcgugin.utility.DisplayUtil
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

fun main(args: Array<String>) {

    val kodein = Kodein {
        bind<FinvizScraper>() with singleton { FinvizScraper() }
        bind<StockService>() with singleton { StockService(kodein) }
        bind<MarketwatchScraper>() with singleton { MarketwatchScraper(kodein) }
        bind<DisplayUtil>() with singleton { DisplayUtil(kodein) }
        bind<FinancialCalculator>() with singleton { FinancialCalculator(kodein) }
    }

    StockService(kodein).findValueStocks()
}