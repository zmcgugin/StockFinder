package com.github.zmcgugin.constants

class Settings {
    companion object {
        //Tweakables
        val FINVIZ_FILTER_URL = "https://finviz.com/screener.ashx?v=111&f=fa_curratio_o3,fa_eps5years_o15,fa_epsyoy1_pos,fa_pe_profitable,fa_pfcf_low,fa_roi_pos,fa_sales5years_pos&ft=2&ty=l&ta=0&p=m&o=price"
        val ALLOWABLE_DEBT_PERCENTAGE = 0.50
        val ALLOWABLE_FREE_CASH_INTRINISC_VALUE_MARGIN = 0


        //Weights
        val FREE_CASH_FLOW_GROWTH_RATE = 0.00
        val DISCOUNT_RATE = 0.105
        val GROWTH_RATE = 0.03
    }
}