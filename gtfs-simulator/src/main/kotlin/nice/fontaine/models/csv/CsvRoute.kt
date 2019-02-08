package nice.fontaine.models.csv

import com.opencsv.bean.CsvBindByName
import nice.fontaine.models.Entity

data class CsvRoute(@CsvBindByName(column = "route_id") override var id: String) : Entity {
    @CsvBindByName(column = "agency_id") var agencyId: Int = -1
    @CsvBindByName(column = "route_short_name") var shortName: String = ""
    @CsvBindByName(column = "route_long_name") var longName: String = ""
    @CsvBindByName(column = "route_type") var type: Int = -1
    @CsvBindByName(column = "route_color") var color: String = ""
    @CsvBindByName(column = "route_text_color") var textColor: String = ""
    @CsvBindByName(column = "route_desc") var description: String = ""
    var agency: CsvAgency? = null

    constructor() : this("")
}
