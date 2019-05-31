package com.github.zmcgugin.finviz.services

import com.github.zmcgugin.finviz.objects.FinvizRow
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class FinvizScraper {

    fun grabHtml(url: String): Document {
        return Jsoup.connect(url).get()
    }

    fun pullStockRows(document: Document): List<FinvizRow> {
        val elements: List<Element> = document.select(".table-dark-row-cp, .table-light-row-cp")

        return elements.filter {isValid(it)}.map {convertOverviewToRow(it)}
    }

    private fun isValid(element: Element): Boolean {
        val elements = element.select("td")
        var valid = true
        elements.filter { it.text() == "-" || it.text() == "" }.forEach { valid = false }
        return valid
    }

    private fun convertOverviewToRow(element: Element): FinvizRow {
        val elements = element.select("td")

        return FinvizRow(
            elements[1].text(),
            elements[2].text(),
            elements[3].text(),
            elements[4].text(),
            elements[5].text(),
            elements[6].text(),
            elements[7].text().toDouble(),
            elements[8].text().toDouble(),
            elements[9].text().replace("%", "").toDouble(),
            elements[10].text().replace(",", "").toDouble()
        )
    }

}