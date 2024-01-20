package com.wahyuhw.authify.utils

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

const val COMPLETE_DATE_AND_DAY_FORMAT = "dd MMM yyyy • HH:mm:ss"
const val DATE_AND_MINUTE_FORMAT:String = "HH:mm"
const val COMPLETE_DAY_YEAR_WITH_SIMPLE_MONTH_FORMAT:String = "dd MMM yyyy"
const val COMPLETE_DATE_FORMAT_WITH_TIMEZONE:String = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"
const val COMPLETE_DATE_NOTIFICATION:String = "yyyy-MM-dd HH:mm:ss"

val localeId = Locale("id", "ID")
val currentDate = Calendar.getInstance(localeId)
val Calendar.month
	get() = this[Calendar.MONTH] + 1
val Calendar.year
	get() = this[Calendar.YEAR]

fun Long.formatUTCEpochToDateString(
	format: String = COMPLETE_DATE_AND_DAY_FORMAT,
	locale: Locale = Locale.US
): String {
	val date = Date()
	date.time = this
	val sdf = SimpleDateFormat(format, locale)
	
	return sdf.format(date)
}

fun String.changeDateStringFormat(oldFormat: String, newFormat: String): String {
	val oldFormatter = DateTimeFormatter.ofPattern(oldFormat, localeId)
	val parsedDate = LocalDateTime.parse(this, oldFormatter)
	
	val newFormatter = DateTimeFormatter.ofPattern(newFormat, localeId)
	return parsedDate.format(newFormatter)
}