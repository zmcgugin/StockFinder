package com.github.zmcgugin

import com.github.zmcgugin.finviz.services.FinvizScraper

fun main(args: Array<String>) {

    //Crap code for now. Need to create UI
    var url: String = "https://finviz.com/screener.ashx?v=111&f=fa_curratio_o3,fa_eps5years_o15,fa_epsyoy1_pos,fa_pe_profitable,fa_pfcf_low,fa_roi_pos,fa_sales5years_pos&ft=2&ty=l&ta=0&p=m&o=price"

    val scraper: FinvizScraper = FinvizScraper()
    val document = scraper.grabHtml(url)

    val results = scraper.pullStockRows(document)


    results.forEach { print("Ticker: ${it.ticker}, Price: ${it.price} \n") }
}