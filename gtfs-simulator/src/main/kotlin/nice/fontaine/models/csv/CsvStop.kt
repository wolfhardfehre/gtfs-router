package nice.fontaine.models.csv

import com.opencsv.bean.CsvBindByName
import nice.fontaine.models.Entity
import org.jxmapviewer.viewer.GeoPosition

data class CsvStop(@CsvBindByName(column = "stop_id") override var id: String) : Entity {
    @CsvBindByName(column = "stop_code") var code: String = ""
    @CsvBindByName(column = "stop_name") var name: String = ""
    @CsvBindByName(column = "stop_desc") var description: String = ""
    @CsvBindByName(column = "stop_lat") var lat: Double = 0.0
    @CsvBindByName(column = "stop_lon") var lon: Double = 0.0
    @CsvBindByName(column = "location_type") var locationType: Int = 0
    @CsvBindByName(column = "parent_station") var parentStation: String = ""

    constructor() : this("")

    fun position() = GeoPosition(lat, lon)
}
