package nice.fontaine.features

import nice.fontaine.extensions.toStopPositions
import nice.fontaine.gui.map.*
import nice.fontaine.models.csv.CsvTrip

class Vessel(private val handler: MapHandler, private val csvTrip: CsvTrip) {
    private val path = csvTrip.path!!
    private val positions = path.positions
    private val stops = path.times.toStopPositions()

    fun update(timestamp: Int) {
        if (!path.timeRange.contains(timestamp)) return
        path.step()
        val style = Element.Point(ORANGE, 200.0)
        handler.update("${csvTrip.id}-VEHICLE", path.currentPosition(), style)
    }

    fun show() {
        handler.updates("${csvTrip.id}-SHAPE", positions, Element.Line(GREEN, 4F))
        handler.updates("${csvTrip.id}-STOPS", stops, Element.Point(BLUE, 50.0))
    }
}
