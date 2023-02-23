package com.mesutemre.kutuphanem_base.util

import java.text.SimpleDateFormat
import java.util.*

fun String.convertStringToDate(
    pattern: String = "dd.MM.yyyy"
): Date? {
    val formatter = SimpleDateFormat(pattern)
    return formatter.parse(this)
}