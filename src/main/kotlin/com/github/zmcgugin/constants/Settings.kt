package com.github.zmcgugin.constants

class Settings {
    companion object {
        //Finviz Filters

        //FINANCIAL FILTERS

        //Value Stocks
//        val FINVIZ_FILTER_URL = "https://finviz.com/screener.ashx?v=111&f=fa_curratio_o3,fa_eps5years_o15,fa_epsyoy1_pos,fa_pe_profitable,fa_pfcf_low,fa_roi_pos,fa_sales5years_pos&ft=2&ty=l&ta=0&p=m&o=price"

        //Profitable Strong Buy Stocks
//        val FINVIZ_FILTER_URL = "https://finviz.com/screener.ashx?v=111&f=an_recom_strongbuy,fa_fpe_profitable,fa_pe_profitable&o=price"

        //TECHNICAL FILTERS

        //This tries to find uptrends that are at the low of the uptrend.
        val FINVIZ_FILTER_URL = "https://finviz.com/screener.ashx?v=111&f=sh_avgvol_o1000,ta_pattern_channelup,ta_perf_1wdown&ft=4&o=-price"

        //Trying to find a stock that will bounce off the SMA20 and return higher to the SMA50
//        val FINVIZ_FILTER_URL = "https://finviz.com/screener.ashx?v=111&f=sh_avgvol_o2000,sh_curvol_o2000,sh_price_10to50,sh_relvol_o1,ta_sma20_pa,ta_sma50_pb&ft=4&o=industry"

        //Simple RSI 30 with relatively decent confirmation of reversal.
//        val FINVIZ_FILTER_URL = "https://finviz.com/screener.ashx?v=111&f=sh_price_o5,sh_relvol_o2,ta_change_u,ta_rsi_os30&ft=4&o=price"

        //Catch all
//        val FINVIZ_FILTER_URL = "https://finviz.com/screener.ashx?v=111&f=sh_avgvol_o2000,sh_price_o20,sh_relvol_o1"




        //Tweakables
        val ALLOWABLE_DEBT_PERCENTAGE = 0.50


        //Weights
        val FREE_CASH_FLOW_GROWTH_RATE = 0.00
        val DISCOUNT_RATE = 0.105
        val GROWTH_RATE = 0.03
    }
}