package nice.fontaine.models.csv

import com.opencsv.bean.CsvBindByName
import nice.fontaine.extensions.toDate
import nice.fontaine.models.Entity
import org.joda.time.DateTime

data class CsvCalendarDate(@CsvBindByName(column = "service_id") override var id: Any) : Entity {
    @CsvBindByName(column = "date") var dateString: String = "19700101"
    @CsvBindByName(column = "exception_type") var exceptionType: Int = 0
    val date: DateTime by lazy { dateString.toDate() }

    constructor() : this("")
}
