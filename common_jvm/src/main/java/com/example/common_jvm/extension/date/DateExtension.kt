package com.example.common_jvm.extension.date

import java.text.SimpleDateFormat
import java.util.*

fun Date.toCalendar(): Calendar {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal
}

fun Calendar.format(format: String): String {
    var dateStr = ""
    val df = SimpleDateFormat(format, Locale.US)
    try {
        dateStr = df.format(this.time)
    } catch (_: Exception) {
        //ignore ex
    }

    return dateStr
}

fun Date.format(format: String): String {
    return this.toCalendar().format(format)
}

fun Calendar.resetMidnight() {
    this.set(Calendar.HOUR, 0)
    this.set(Calendar.MINUTE, 0)
    this.set(Calendar.SECOND, 0)
}

fun Date.isFuture(): Boolean {
    return !Date().before(this)
}

fun Calendar.isFuture(): Boolean {
    return this.time.isFuture()
}

fun Date.isPast(): Boolean {
    return Date().before(this)
}

fun Calendar.isPast(): Boolean {
    return this.time.isPast()
}

fun Calendar.isToday(): Boolean {
    val todayCalendar = Date().toCalendar()
    return this.isSameDay(todayCalendar)
}

fun Date.isToday(): Boolean {
    return this.toCalendar().isToday()
}

fun Date.isSameDay(otherDate: Date): Boolean {
    return this.toCalendar().isSameDay(otherDate.toCalendar())
}

fun Calendar.isSameDay(otherCalendar: Calendar): Boolean {
    return this.year() == otherCalendar.year()
            && this.month() == otherCalendar.month()
            && this.day() == otherCalendar.day()
}

fun Calendar.isYesterday(): Boolean {
    val yesterdayCalendar = Date().toCalendar()
    yesterdayCalendar.add(Calendar.DATE, -1)
    return this.isSameDay(yesterdayCalendar)
}

fun Date.isYesterday(): Boolean {
    return this.toCalendar().isYesterday()
}

fun Calendar.isTomorrow(): Boolean {
    val tomorrowCalendar = Date().toCalendar()
    tomorrowCalendar.add(Calendar.DATE, 1)
    return this.isSameDay(tomorrowCalendar)
}

fun Date.isTomorrow(): Boolean {
    return this.toCalendar().isTomorrow()
}

fun Date.hour(): Int {
    return this.toCalendar().hour()
}

fun Date.minute(): Int {
    return this.toCalendar().minute()
}

fun Date.second(): Int {
    return this.toCalendar().second()
}

fun Date.month(): Int {
    return this.toCalendar().month()
}

fun Date.year(): Int {
    return this.toCalendar().year()
}

fun Date.day(): Int {
    return this.toCalendar().day()
}

fun Date.dayOfWeek(): Int {
    return this.toCalendar().dayOfWeek()
}

fun Date.dayOfYear(): Int {
    return this.toCalendar().dayOfYear()
}

fun Calendar.hour(): Int {
    return this.get(Calendar.HOUR)
}

fun Calendar.minute(): Int {
    return this.get(Calendar.MINUTE)
}

fun Calendar.second(): Int {
    return this.get(Calendar.SECOND)
}

fun Calendar.month(): Int {
    return this.get(Calendar.MONTH) + 1
}

fun Calendar.year(): Int {
    return this.get(Calendar.YEAR)
}

fun Calendar.day(): Int {
    return this.get(Calendar.DAY_OF_MONTH)
}

fun Calendar.dayOfWeek(): Int {
    return this.get(Calendar.DAY_OF_WEEK)
}

fun Calendar.dayOfYear(): Int {
    return this.get(Calendar.DAY_OF_YEAR)
}