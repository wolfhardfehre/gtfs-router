package nice.fontaine.models.base

import nice.fontaine.models.csv.CsvStopTime
import nice.fontaine.extensions.toSecs
import org.joda.time.Period

class TimeRange(start: CsvStopTime, end: CsvStopTime) {
    private val departure = start.departure.toSecs()
    private val arrival = end.arrival.toSecs()
    private val range = departure..arrival

    fun contains(secs: Period) = contains(secs.toSecs())

    fun contains(secs: Int) = secs in range
}
