package com.example.myfirstwork.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


fun String.toKoreanDate() : String {
    //
    val date = LocalDate.parse(this)
    val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 EEEE",
        Locale.KOREAN)

    return date.format(formatter)
}