package nice.fontaine.models.csv

import com.opencsv.bean.CsvBindByName
import nice.fontaine.models.Entity
import org.joda.time.DateTimeZone

data class CsvAgency(@CsvBindByName(column = "agency_id") override var id: Int) : Entity {
    @CsvBindByName(column = "agency_name") var name: String = ""
    @CsvBindByName(column = "agency_url") var url: String = ""
    @CsvBindByName(column = "agency_timezone") var tZone: String = "UTC"
    @CsvBindByName(column = "agency_lang") var language: String = "en"
    @CsvBindByName(column = "agency_phone") var phone: String = ""

    constructor() : this(-1)

    fun timezone() = DateTimeZone.forID(tZone)!!
}
