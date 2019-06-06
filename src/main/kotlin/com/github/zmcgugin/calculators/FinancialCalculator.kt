package com.github.zmcgugin.calculators

import com.github.zmcgugin.constants.Settings
import com.github.zmcgugin.objects.StockInformation
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow

class FinancialCalculator(override val kodein: Kodein) : KodeinAware {

    fun calculateBookValue(it: StockInformation): Double {
        return round((it.assets - it.liabilities) / it.sharesOutstanding)
    }

    fun calculateCashFlowIntrensicMargin(it: StockInformation): Double {
        return round(((it.freeCashFlowIntrinsicValue - it.price) / it.price) * 100)
    }

    fun calculateCashFlowIntrinsicValue(it: StockInformation): Double {
        var tenthValue = 0.0
        var total = 0.0
        var value: Double

        for (i in 1..11) {
            if(i == 11) {
                value = (tenthValue * (1 + Settings.GROWTH_RATE)) / (Settings.DISCOUNT_RATE - Settings.GROWTH_RATE)
            } else {
                value = (it.last12MonthsFreeCashFlow * (1 + Settings.FREE_CASH_FLOW_GROWTH_RATE).pow(i - 1)) / ((1 + Settings.DISCOUNT_RATE).pow(i))
            }
            if(i == 10) {
                tenthValue = value
            }
            total += value
        }

        return round(total / it.sharesOutstanding)
    }

    fun round(it: Double): Double {
        return BigDecimal(it).setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }
}