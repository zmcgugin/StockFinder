package com.github.zmcgugin.utility

class Convertor {

    companion object {
        fun convertStringToNumber(value: String): Double {
            var parsedValue: String = value
            parsedValue = parsedValue.replace(",", "")
            parsedValue = parsedValue.replace("%", "")
            parsedValue = parsedValue.replace("$", "")
            if(parsedValue.equals("-")) {
                return 0.0
            }
            if(parsedValue.contains("(")) {
                parsedValue = parsedValue.replace("(", "")
                parsedValue = parsedValue.replace(")", "")
                parsedValue = "-$parsedValue"
            }
            if(parsedValue.contains("M")) {
                parsedValue = parsedValue.replace("M", "")
                return parsedValue.toDouble() * 1_000_000
            }
            if(parsedValue.contains("B")) {
                parsedValue = parsedValue.replace("B", "")
                return parsedValue.toDouble() * 1_000_000_000
            }
            return parsedValue.toDouble()
        }
    }
}