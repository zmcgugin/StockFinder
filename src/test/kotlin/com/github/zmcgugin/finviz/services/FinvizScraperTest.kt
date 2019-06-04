package com.github.zmcgugin.finviz.services

import com.github.zmcgugin.objects.StockInformation
import org.apache.commons.io.FileUtils
import org.jsoup.Jsoup
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File
import java.nio.charset.Charset

class FinvizScraperTest {

    @Test
    fun `findValueStocks should page though all finviz results`() {
        val html = FileUtils.readFileToString(File("src/test/resources/finvizSample.html"), Charset.defaultCharset())
        var document = Jsoup.parse(html)

        val result: List<StockInformation> = FinvizScraper().pullStockRows(document)

        assertEquals(result.size, 20)

        assertEquals(result.get(0).ticker, "HNNA")
        assertEquals(result.get(0).company, "Hennessy Advisors, Inc.")
        assertEquals(result.get(0).sector, "Financial")
        assertEquals(result.get(0).industry, "Asset Management")
        assertEquals(result.get(0).country, "USA")
        assertEquals(result.get(0).marketCap, 72_220_000.0, 0.0)
        assertEquals(result.get(0).pE, 5.43, 0.0)
        assertEquals(result.get(0).price, 9.44, 0.0)
        assertEquals(result.get(0).change, -0.48, 0.0)
        assertEquals(result.get(0).volume, 2449.0, 0.0)

        assertEquals(result.get(10).ticker, "NANO")
        assertEquals(result.get(10).price, 29.02, 0.0)

        assertEquals(result.get(19).ticker, "SBGI")
        assertEquals(result.get(19).price, 55.73, 0.0)
    }
}
