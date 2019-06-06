package com.github.zmcgugin.utility

import com.github.zmcgugin.constants.Settings.Companion.ALLOWABLE_DEBT_PERCENTAGE
import com.github.zmcgugin.constants.Settings.Companion.ALLOWABLE_FREE_CASH_INTRINISC_VALUE_MARGIN
import com.github.zmcgugin.objects.StockInformation
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class DisplayUtil(override val kodein: Kodein) : KodeinAware {

    fun displayResults(stocks: List<StockInformation>) {
        println("\n\nValue buys:\n")
        stocks.filter { meetsCriteria(it) }.forEach {
            println(
                "Ticker: ${it.ticker},\t\t" +
                "Price: ${it.price},\t\t " +
                "Volume: ${it.volume}" +
                "Book Value: ${it.bookValue},\t\t" +
                "Intrinsic Cash Value: ${it.freeCashFlowIntrinsicValue},\t\t"


            )
        }
    }

    private fun meetsCriteria(it: StockInformation): Boolean {
        return ((it.liabilities / it.assets) < ALLOWABLE_DEBT_PERCENTAGE) && (it.freeCashFlowIntrinsicMarginOfSafety > ALLOWABLE_FREE_CASH_INTRINISC_VALUE_MARGIN)
    }

}