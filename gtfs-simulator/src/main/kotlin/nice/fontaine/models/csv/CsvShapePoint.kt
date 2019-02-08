package nice.fontaine.models.csv

import com.opencsv.bean.CsvBindByName
import org.jxmapviewer.viewer.GeoPosition

data class CsvShapePoint(@CsvBindByName(column = "shape_id") var id: Int,
                         @CsvBindByName(column = "shape_pt_sequence") var seqId: Int) {
    @CsvBindByName(column = "shape_pt_lat") var lat: Double = 0.0
    @CsvBindByName(column = "shape_pt_lon") var lon: Double = 0.0
    val position: GeoPosition by lazy { GeoPosition(lat, lon) }

    constructor() : this(-1, -1)
}
