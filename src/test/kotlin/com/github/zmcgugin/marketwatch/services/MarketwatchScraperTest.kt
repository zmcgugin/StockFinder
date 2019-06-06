package com.github.zmcgugin.marketwatch.services

import com.github.zmcgugin.objects.StockInformation
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.spyk
import org.apache.commons.io.FileUtils
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.Kodein
import java.io.File
import java.nio.charset.Charset

@ExtendWith(MockKExtension::class)
class MarketwatchScraperTest {

    val kodein = Kodein {}
    val subject: MarketwatchScraper = spyk(MarketwatchScraper(kodein))

    @Test
    fun lookupSummaryInformation() {
        val html = FileUtils.readFileToString(File("src/test/resources/marketWatchSummary.html"), Charset.defaultCharset())
        every { subject.grabHtml("https://www.marketwatch.com/investing/stock/ticker") } returns Jsoup.parse(html)
        val row = StockInformation()
        row.ticker = "ticker"

        subject.lookupSummaryInformation(row)

        assertEquals(4_720_000_000.0, row.sharesOutstanding, 0.0000001)
        assertEquals(11.89, row.eps, 0.0000001)
    }

    @Test
    fun lookupCashFlow() {
        val html = FileUtils.readFileToString(File("src/test/resources/marketWatchCashFlow.html"), Charset.defaultCharset())
        every { subject.grabHtml("https://www.marketwatch.com/investing/stock/ticker/financials/cash-flow/quarter") } returns Jsoup.parse(html)
        val row = StockInformation()
        row.ticker = "ticker"

        subject.lookupCashFlow(row)

        assertEquals(59_830_000_000.0, row.last12MonthsFreeCashFlow, 0.0000001)
    }

    @Test
    fun lookupDebt() {
        val html = FileUtils.readFileToString(File("src/test/resources/marketWatchBalanceSheet.html"), Charset.defaultCharset())
        every { subject.grabHtml("https://www.marketwatch.com/investing/stock/ticker/financials/balance-sheet/quarter") } returns Jsoup.parse(html)
        val row = StockInformation()
        row.ticker = "ticker"

        subject.lookupDebt(row)

        assertEquals(365_730_000_000.0, row.assets, 0.0000001)
        assertEquals(258_579_999_999.9, row.liabilities, 0.1)
    }
}