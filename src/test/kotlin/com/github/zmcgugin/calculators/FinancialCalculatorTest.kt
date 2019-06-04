package com.github.zmcgugin.calculators

import com.github.zmcgugin.objects.StockInformation
import org.junit.Assert.assertEquals
import org.junit.Test
import org.kodein.di.Kodein
import org.mockito.Mockito.mock

class FinancialCalculatorTest {

    val financialCalculator: FinancialCalculator = FinancialCalculator(mock(Kodein::class.java))

    @Test
    fun `calculateIntrinsicValue should calculate intrinsic value of stock_1`() {
        val row = StockInformation()
        row.last12MonthsFreeCashFlow = 1_700_000_000.0
        row.sharesOutstanding = 230_440_000.0
        row.price = 12.12

        val calculateIntrinsicValue = financialCalculator.calculateCashFlowIntrinsicValue(row)

        assertEquals(81.70, calculateIntrinsicValue.freeCashFlowIntrinsicValue, 0.01)
        assertEquals(574.09, calculateIntrinsicValue.freeCashFlowIntrinsicMarginOfSafety, 0.01)
    }

    @Test
    fun `calculateIntrinsicValue should calculate intrinsic value of stock_2`() {
        val row = StockInformation()
        row.last12MonthsFreeCashFlow = 5_773_000_000.0
        row.sharesOutstanding = 27_700_000.0
        row.price = 23.43

        val calculateIntrinsicValue = financialCalculator.calculateCashFlowIntrinsicValue(row)

        assertEquals(2308.12, calculateIntrinsicValue.freeCashFlowIntrinsicValue, 0.01)
        assertEquals(9751.13, calculateIntrinsicValue.freeCashFlowIntrinsicMarginOfSafety, 0.01)
    }
}