package com.argumelar.newsapp.util

import java.text.SimpleDateFormat
import java.util.Locale

class DateFormat {
    fun dateFormat(date: String): String{
        return if (date.isNullOrEmpty())""
        else {
            val currenFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:mm'Z'", Locale.getDefault())
            val dateParse = currenFormat.parse(date)
            val toFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            toFormat.format(dateParse)
        }
    }
}