package com.github.zmcgugin.marketwatch.services

import com.github.zmcgugin.calculators.FinancialCalculator
import com.github.zmcgugin.objects.StockInformation
import com.github.zmcgugin.utility.Convertor
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class MarketwatchScraper(override val kodein: Kodein) : KodeinAware {

    val financialCalculator: FinancialCalculator by kodein.instance()

    fun grabHtml(url: String): Document {
        return Jsoup.connect(url).get()
    }

    fun updateFinancials(row: StockInformation): StockInformation {
        lookupSummaryInformation(row)
        lookupCashFlow(row)
        lookupDebt(row)
        row.freeCashFlowIntrinsicValue = financialCalculator.calculateCashFlowIntrinsicValue(row)
        row.freeCashFlowIntrinsicMarginOfSafety = financialCalculator.calculateCashFlowIntrensicMargin(row)
        row.bookValue = financialCalculator.calculateBookValue(row)
        row.grahamsValue = financialCalculator.calculateGrahamsValue(row)
        row.debtRatio = financialCalculator.calculateDebtRatio(row)

        return row
    }

    fun lookupSummaryInformation(row: StockInformation) {
        println("Looking up summary page for ${row.ticker}")
        val summaryDocument = grabHtml("https://www.marketwatch.com/investing/stock/${row.ticker}")
        val sharesOutstanding = summaryDocument.select(".kv__item").filter { it.text().contains("Shares Outstanding") }[0].text().replace("Shares Outstanding ", "")
        val eps = summaryDocument.select(".kv__item").filter { it.text().contains("EPS") }[0].text().replace("EPS ", "")

        row.sharesOutstanding = Convertor.convertStringToNumber(sharesOutstanding)
        row.eps = Convertor.convertStringToNumber(eps)
    }

    fun lookupCashFlow(row: StockInformation) {
        println("Looking up cashflow page for ${row.ticker}")
        val cashFlowDocument =
            grabHtml("https://www.marketwatch.com/investing/stock/${row.ticker}/financials/cash-flow/quarter")
        val cashflowTableRow =
            cashFlowDocument.select("tr.mainRow").filter { it.text().contains("Free Cash Flow") }[0].select("td")
        val firstQuarterCashFlow = Convertor.convertStringToNumber(cashflowTableRow[2].text())
        val secondQuarterCashFlow = Convertor.convertStringToNumber(cashflowTableRow[3].text())
        val thirdQuarterCashFlow = Convertor.convertStringToNumber(cashflowTableRow[4].text())
        val fourthQuarterCashFlow = Convertor.convertStringToNumber(cashflowTableRow[5].text())
        row.last12MonthsFreeCashFlow = firstQuarterCashFlow + secondQuarterCashFlow + thirdQuarterCashFlow + fourthQuarterCashFlow
    }

    fun lookupDebt(row: StockInformation) {
        println("Looking up balancesheet page for ${row.ticker}")
        val balanceSheetDocument =
            grabHtml("https://www.marketwatch.com/investing/stock/${row.ticker}/financials/balance-sheet/quarter")
        val totals = balanceSheetDocument.select("tr.totalRow")
        val totalAssetsRow = totals.filter { it.text().contains("Total Assets") }[0].select("td")
        row.assets = Convertor.convertStringToNumber(totalAssetsRow[5].text())

        val totalLiabilitiesRow = totals.filter { it.text().contains("Total Liabilities") }[0].select("td")
        row.liabilities = Convertor.convertStringToNumber(totalLiabilitiesRow[5].text())
    }

}