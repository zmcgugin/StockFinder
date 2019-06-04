package com.github.zmcgugin.execution

import com.github.zmcgugin.constants.Settings
import com.github.zmcgugin.finviz.services.FinvizScraper
import com.github.zmcgugin.marketwatch.services.MarketwatchScraper
import com.github.zmcgugin.objects.StockInformation
import com.github.zmcgugin.utility.DisplayUtil
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class StockService(override val kodein: Kodein) : KodeinAware {

    val finvizScraper: FinvizScraper by kodein.instance()
    val marketwatchScraper: MarketwatchScraper by kodein.instance()
    val display: DisplayUtil by kodein.instance()

    fun findValueStocks(): List<StockInformation> {
        var results: MutableList<StockInformation> = finvizScraper.getStocks(Settings.FINVIZ_FILTER_URL)
        val finalResults = results.map { marketwatchScraper.updateFinancials(it)}

        display.displayResults(finalResults)
        return finalResults
    }

}