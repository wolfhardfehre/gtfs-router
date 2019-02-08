package nice.fontaine.models.csv

import com.opencsv.bean.CsvBindByName
import nice.fontaine.models.Entity

data class CsvTransfer(@CsvBindByName(column = "from_stop_id") var fromStopId: String,
                       @CsvBindByName(column = "to_stop_id") var toStopId: String) : Entity {
    @CsvBindByName(column = "transfer_type") var type: Int = 0
    @CsvBindByName(column = "min_transfer_time") var seconds: Int = 0
    @CsvBindByName(column = "from_route_id") var fromRouteId: String = ""
    @CsvBindByName(column = "to_route_id") var toRouteId: String = ""
    @CsvBindByName(column = "from_trip_id") var fromTripId: String = ""
    @CsvBindByName(column = "to_trip_id") var toTripId: String = ""
    override val id: String by lazy { "${fromStopId}_$toStopId" }

    constructor() : this("", "")
}
