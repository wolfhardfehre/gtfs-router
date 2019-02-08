package nice.fontaine.models.csv

import com.opencsv.bean.CsvBindByName
import nice.fontaine.features.Path
import nice.fontaine.models.Entity

class CsvTrip(@CsvBindByName(column = "trip_id") override var id: String) : Entity {
    @CsvBindByName(column = "route_id") var routeId: String = ""
    @CsvBindByName(column = "service_id") var serviceId: Int = -1
    @CsvBindByName(column = "trip_short_name") var shortName: String = ""
    @CsvBindByName(column = "trip_headsign") var headsign: String = ""
    @CsvBindByName(column = "direction_id") var directionId: Int = -1
    @CsvBindByName(column = "block_id") var blockId: Int = -1
    @CsvBindByName(column = "shape_id") var shapeId: Int = -1
    @CsvBindByName(column = "wheelchair_accessible") var wheelchairAccessible: Boolean = false
    @CsvBindByName(column = "bikes_allowed") var bikesAllowed: Boolean = false
    var route: CsvRoute? = null
    var path: Path? = null

    var calendar: CsvCalendar? = null
    var exclusionDates: Array<CsvCalendarDate>? = null

    constructor() : this("")
}
