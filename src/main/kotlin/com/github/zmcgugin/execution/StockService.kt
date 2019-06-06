package com.github.zmcgugin.execution

import com.github.zmcgugin.constants.Settings
import com.github.zmcgugin.finviz.services.FinvizScraper
import com.github.zmcgugin.marketwatch.services.MarketwatchScraper
import com.github.zmcgugin.objects.StockInformation
import com.github.zmcgugin.utility.DisplayUtil
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import java.util.stream.Collectors

class StockService(override val kodein: Kodein) : KodeinAware {

    val finvizScraper: FinvizScraper by kodein.instance()
    val marketwatchScraper: MarketwatchScraper by kodein.instance()
    val display: DisplayUtil by kodein.instance()
//    val threadpool: ForkJoinPool = ForkJoinPool(THREAD_COUNT)

    fun findValueStocks(): List<StockInformation> {

        var results: MutableList<StockInformation> = finvizScraper.getStocks(Settings.FINVIZ_FILTER_URL)
        //TODO using parallel stream for now with common threadpool because there is a weird error with forkthreadpool that i'll figure out later.
//      var finalResults = threadpool.submit{results.parallelStream().map{marketwatchScraper.updateFinancials(it)}.collect(Collectors.toList())}.get(5, TimeUnit.MINUTES)
        var finalResults = results.parallelStream().map{marketwatchScraper.updateFinancials(it)}.collect(Collectors.toList())

        display.displayResults(finalResults as List<StockInformation>)
        return finalResults
    }

}