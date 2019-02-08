package nice.fontaine.models.csv

import com.opencsv.bean.CsvBindByName
import nice.fontaine.extensions.toDate
import nice.fontaine.models.Entity
import org.joda.time.DateTime
import org.joda.time.Interval

data class CsvCalendar(@CsvBindByName(column = "service_id") override var id: String) : Entity {
    @CsvBindByName(column = "monday") var moday: Boolean = false
    @CsvBindByName(column = "tuesday") var tuesday: Boolean = false
    @CsvBindByName(column = "wednesday") var wednesday: Boolean = false
    @CsvBindByName(column = "thursday") var thursday: Boolean = false
    @CsvBindByName(column = "friday") var friday: Boolean = false
    @CsvBindByName(column = "saturday") var saturday: Boolean = false
    @CsvBindByName(column = "sunday") var sunday: Boolean = false
    @CsvBindByName(column = "start_date") var startDate: String = "19700101"
    @CsvBindByName(column = "end_date") var endDate: String = "19700101"
    private val dateRange: Interval by lazy { Interval(startDate.toDate(), endDate.toDate()) }

    constructor() : this("")

    fun contains(date: DateTime) = dateRange.contains(date)
}
