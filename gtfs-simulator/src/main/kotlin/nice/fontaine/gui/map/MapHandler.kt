package nice.fontaine.gui.map

import org.jxmapviewer.viewer.GeoPosition

class MapHandler(private val mapView: MapView) {
    private val elements = mutableMapOf<Pair<String, Element>, MutableList<GeoPosition>>()

    init {
        mapView.focus(GeoPosition(52.517788, 13.403991))
    }

    @Synchronized fun update(id: String, position: GeoPosition, element: Element) {
        updates(id, mutableListOf(position), element)
    }

    @Synchronized fun updates(id: String, positions: MutableList<GeoPosition>, element: Element) {
        updateElements(id, positions, element)
        elements.forEach { entry -> drawElement(entry) }
        mapView.draw()
    }

    @Synchronized fun reset() {
        elements.clear()
    }

    private fun updateElements(id: String, positions: MutableList<GeoPosition>, element: Element) {
        val key = Pair(id, element)
        if (elements.containsKey(key)) elements[key]!!.addAll(positions)
        else elements[key] = positions
    }

    private fun drawElement(entry: Map.Entry<Pair<String, Element>, MutableList<GeoPosition>>) {
        val element = entry.key.second
        val id = entry.key.first
        when (element) {
            is Element.Point -> mapView.points("${id}_POINT", entry.value, element.diameter, element.color)
            is Element.Diminish -> mapView.diminish("${id}_DIMINISH", entry.value, element.diameter, element.color)
            is Element.Line -> mapView.line("${id}_LINE", entry.value, element.width, element.color)
        }
    }
}
