package com.github.zmcgugin.calculators

import com.github.zmcgugin.objects.StockInformation
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.kodein.di.Kodein

class FinancialCalculatorTest {

    val kodein = Kodein {}
    val subject: FinancialCalculator = spyk(FinancialCalculator(kodein))

    @Test
    fun `calculateIntrinsicValue should calculate intrinsic value of stock_1`() {
        val row = StockInformation()
        row.last12MonthsFreeCashFlow = 1_700_000_000.0
        row.sharesOutstanding = 230_440_000.0
        row.price = 12.12
        row.assets = 900_000_000.0
        row.liabilities = 100_000_000.0

        row.freeCashFlowIntrinsicValue = subject.calculateCashFlowIntrinsicValue(row)

        assertEquals(81.70, subject.calculateCashFlowIntrinsicValue(row), 0.01)
        assertEquals(574.09, subject.calculateCashFlowIntrensicMargin(row), 0.01)
        assertEquals(3.47, subject.calculateBookValue(row), 0.01)
    }

    @Test
    fun `calculateIntrinsicValue should calculate intrinsic value of stock_2`() {
        val row = StockInformation()
        row.last12MonthsFreeCashFlow = 5_773_000_000.0
        row.sharesOutstanding = 27_700_000.0
        row.price = 23.43

        row.freeCashFlowIntrinsicValue = subject.calculateCashFlowIntrinsicValue(row)

        assertEquals(2308.12, subject.calculateCashFlowIntrinsicValue(row), 0.01)
        assertEquals(9751.13, subject.calculateCashFlowIntrensicMargin(row), 0.01)
    }
}