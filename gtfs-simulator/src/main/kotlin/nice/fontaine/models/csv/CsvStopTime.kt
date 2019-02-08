package nice.fontaine.models.csv

import com.opencsv.bean.CsvBindByName
import nice.fontaine.extensions.toPeriod
import nice.fontaine.models.Entity
import org.joda.time.Period

data class CsvStopTime(@CsvBindByName(column = "trip_id") var tripId: String,
                       @CsvBindByName(column = "stop_sequence") var seqId: Int) : Entity {
    @CsvBindByName(column = "arrival_time") var arrivalTime: String = ""
    @CsvBindByName(column = "departure_time") var departureTime: String = ""
    @CsvBindByName(column = "stop_id") var stopId: String = ""
    @CsvBindByName(column = "pickup_type") var pickupType: Int = -1
    @CsvBindByName(column = "drop_off_type") var dropOffType: Int = -1
    @CsvBindByName(column = "stop_headsign") var headsign: String = ""
    override val id: String by lazy { "${tripId}_$seqId" }
    val arrival: Period by lazy { arrivalTime.toPeriod() }
    val departure: Period by lazy { departureTime.toPeriod() }
    var stop: CsvStop? = null

    constructor() : this("", -1)
}
