package com.github.zmcgugin.execution

import com.github.zmcgugin.finviz.objects.FinvizRow
import com.github.zmcgugin.finviz.services.FinvizScraper
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class StockService(override val kodein: Kodein) : KodeinAware {

    val BASE_URL = "https://finviz.com/screener.ashx?v=111&f=fa_curratio_o3,fa_eps5years_o15,fa_epsyoy1_pos,fa_pe_profitable,fa_pfcf_low,fa_roi_pos,fa_sales5years_pos&ft=2&ty=l&ta=0&p=m&o=price"
    val BASE_URL_EXTENSION = "&r="
    val INCREMENT_AMOUNT = 20
    val BASE_AMOUNT = 1
    val scraper: FinvizScraper by kodein.instance()



    fun findValueStocks(): List<FinvizRow> {
        //Crap code for now. Need to create UI
        var results: MutableList<FinvizRow> = mutableListOf()
        var swapList: List<FinvizRow>
        var counter = 0

        do {
            val document = scraper.grabHtml(BASE_URL + BASE_URL_EXTENSION + (counter + BASE_AMOUNT))
            swapList = scraper.pullStockRows(document)
            results.addAll(swapList)
            counter += INCREMENT_AMOUNT
        } while(swapList.size > 1)

        results.forEach { print("Ticker: ${it.ticker},\t\t Price: ${it.price},\t\t Market Cap: ${it.marketCap},\t\t Volume: ${it.volume} \n") }
        return results
    }
}