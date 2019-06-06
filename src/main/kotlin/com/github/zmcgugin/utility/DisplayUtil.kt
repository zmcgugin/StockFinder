package com.github.zmcgugin.utility

import com.github.zmcgugin.calculators.FinancialCalculator.Companion.round
import com.github.zmcgugin.constants.Settings.Companion.ALLOWABLE_DEBT_PERCENTAGE
import com.github.zmcgugin.objects.StockInformation
import de.vandermeer.asciitable.AsciiTable
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class DisplayUtil(override val kodein: Kodein) : KodeinAware {

    fun displayResults(stocks: List<StockInformation>) {
        print("\n\n\n\n\n\n\n\n\n")

        var table = AsciiTable()
        table.addRule()
        table.addRow("Ticker","Price","Volume","Debt Ratio","Book Value", "Graham's Value", "P/E Stock Value", "Intrinsic Cash Value")
        table.addRule()

        stocks.filter { meetsCriteria(it) }.sortedBy { sortByPotental(it) }.forEach {
            table.addRow(
                "${it.ticker}",
                "$${it.price}",
                "${round(it.volume / 1_000)}K",
                "${it.debtRatio}",
                highlight(it.bookValue, it.price),
                highlight(it.grahamsValue, it.price),
                highlight(it.priceToEarningsPerStockValue, it.price),
                highlight(it.freeCashFlowIntrinsicValue, it.price))
            table.addRule()
        }


        print(table.render())
    }

    private fun sortByPotental(it: StockInformation): Int {
        var potential = 0
        if(it.bookValue > it.price) potential++
        if(it.grahamsValue > it.price) potential++
        if(it.priceToEarningsPerStockValue > it.price) potential++
        if(it.freeCashFlowIntrinsicValue > it.price) potential++
        return potential * -1
    }

    private fun highlight(estimate: Double, price: Double): Any? {
        var result = "$${estimate} "
        if(estimate > price) {
            return result + "<--"
        }
        return result
    }

    private fun meetsCriteria(it: StockInformation): Boolean {
        return ((it.liabilities / it.assets) < ALLOWABLE_DEBT_PERCENTAGE) &&
                (it.bookValue > it.price || it.grahamsValue > it.price || it.priceToEarningsPerStockValue > it.price || it.freeCashFlowIntrinsicValue > it.price)
    }

}