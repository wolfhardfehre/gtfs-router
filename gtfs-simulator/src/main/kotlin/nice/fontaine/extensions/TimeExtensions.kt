package nice.fontaine.extensions

import org.joda.time.DateTime
import org.joda.time.Period
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.PeriodFormatterBuilder
import org.postgresql.util.PGInterval

private val COMPACT_DATE_FORMATTER = DateTimeFormat.forPattern("yyyyMMdd")!!
private val DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd")!!
val PERIOD_FORMATTER = PeriodFormatterBuilder().appendHours().appendSeparator(":")
        .appendMinutes().appendSeparator(":").appendSeconds().toFormatter()!!

fun String.toPeriod() = PERIOD_FORMATTER.parsePeriod(this)!!

fun String.toSeconds() = this.toPeriod().toSecs()

fun String.toDate() = when (this.length) {
    8 -> DateTime.parse(this, COMPACT_DATE_FORMATTER)
    10 -> DateTime.parse(this, DATE_FORMATTER)
    else -> DateTime.now()
}!!

fun Period.toSecs() = this.toStandardSeconds().seconds

fun PGInterval.toPeriod() = Period(
        this.years, this.months, 0, this.days,
        this.hours, this.minutes, this.seconds.toInt(), 0)
