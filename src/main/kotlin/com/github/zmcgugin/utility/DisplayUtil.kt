package com.github.zmcgugin.utility

import com.github.zmcgugin.constants.Settings.Companion.ALLOWABLE_DEBT_PERCENTAGE
import com.github.zmcgugin.constants.Settings.Companion.ALLOWABLE_FREE_CASH_INTRINISC_VALUE_MARGIN
import com.github.zmcgugin.objects.StockInformation
import de.vandermeer.asciitable.AsciiTable
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class DisplayUtil(override val kodein: Kodein) : KodeinAware {

    fun displayResults(stocks: List<StockInformation>) {
        print("\n\n\n\n\n\n\n\n\n")

        var table = AsciiTable()
        table.addRule()
        table.addRow("Ticker","Price","Volume","Debt Ratio","Book Value", "Graham's Value", "Intrinsic Cash Value")
        table.addRule()

        stocks.filter { meetsCriteria(it) }.forEach {
            table.addRow(
                "${it.ticker}",
                "$${it.price}",
                "${it.volume / 1000}k",
                "${it.debtRatio}",
                highlight(it.bookValue, it.price),
                highlight(it.grahamsValue, it.price),
                highlight(it.freeCashFlowIntrinsicValue, it.price))
            table.addRule()
        }


        print(table.render())
    }

    private fun highlight(estimate: Double, price: Double): Any? {
        var result = "$${estimate} "
        if(estimate > price) {
            return result + "<--"
        }
        return result
    }

    private fun meetsCriteria(it: StockInformation): Boolean {
        return ((it.liabilities / it.assets) < ALLOWABLE_DEBT_PERCENTAGE) && (it.freeCashFlowIntrinsicMarginOfSafety > ALLOWABLE_FREE_CASH_INTRINISC_VALUE_MARGIN)
    }

}