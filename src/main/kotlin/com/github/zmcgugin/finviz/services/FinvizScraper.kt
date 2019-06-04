package com.github.zmcgugin.finviz.services

import com.github.zmcgugin.objects.StockInformation
import com.github.zmcgugin.utility.Convertor
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class FinvizScraper {

    val BASE_URL_EXTENSION = "&r="
    val INCREMENT_AMOUNT = 20
    val BASE_AMOUNT = 1

    fun getStocks(url: String): MutableList<StockInformation> {
        var results: MutableList<StockInformation> = mutableListOf()
        var swapList: List<StockInformation>
        var counter = 0
        do {
            val document = grabHtml(url + BASE_URL_EXTENSION + (counter + BASE_AMOUNT))
            swapList = pullStockRows(document)
            results.addAll(swapList)
            counter += INCREMENT_AMOUNT
        } while (swapList.size > 1)
        return results
    }

    fun grabHtml(url: String): Document {
        println("Looking up finviz page")
        return Jsoup.connect(url).get()
    }

    fun pullStockRows(document: Document): List<StockInformation> {
        val elements: List<Element> = document.select(".table-dark-row-cp, .table-light-row-cp")
        return elements.filter {isValid(it)}.map {convertOverviewToRow(it)}
    }

    private fun isValid(element: Element): Boolean {
        val elements = element.select("td")
        var valid = true
        elements.filter { it.text() == "-" || it.text() == "" }.forEach { valid = false }
        return valid
    }

    private fun convertOverviewToRow(element: Element): StockInformation {
        val elements = element.select("td")
        val finvizRow = StockInformation()

        finvizRow.ticker = elements[1].text()
        finvizRow.company = elements[2].text()
        finvizRow.sector = elements[3].text()
        finvizRow.industry = elements[4].text()
        finvizRow.country = elements[5].text()
        finvizRow.marketCap = Convertor.convertStringToNumber(elements[6].text())
        finvizRow.pE = Convertor.convertStringToNumber(elements[7].text())
        finvizRow.price = Convertor.convertStringToNumber(elements[8].text())
        finvizRow.change = Convertor.convertStringToNumber(elements[9].text())
        finvizRow.volume = Convertor.convertStringToNumber(elements[10].text())

        return finvizRow
    }

}