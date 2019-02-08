package nice.fontaine.gui.map

import nice.fontaine.gui.map.painters.DiminishingPoints
import nice.fontaine.gui.map.painters.Line
import nice.fontaine.gui.map.painters.Points
import nice.fontaine.gui.map.styles.MapStyle
import nice.fontaine.gui.map.styles.Style
import org.jxmapviewer.JXMapViewer
import org.jxmapviewer.input.CenterMapListener
import org.jxmapviewer.input.PanKeyListener
import org.jxmapviewer.input.PanMouseInputListener
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor
import org.jxmapviewer.painter.CompoundPainter
import org.jxmapviewer.painter.Painter
import org.jxmapviewer.viewer.DefaultTileFactory
import org.jxmapviewer.viewer.GeoPosition
import java.awt.Color
import java.util.concurrent.LinkedBlockingQueue

class MapView(token: String, mapStyle: MapStyle) : JXMapViewer() {
    private val collection: MutableMap<String, Painter<JXMapViewer>> = mutableMapOf()
    private lateinit var current: GeoPosition

    init {
        val pan = PanMouseInputListener(this)
        val provider = Mapbox(token, mapStyle.tileStyle)
        addMouseListener(pan)
        addMouseMotionListener(pan)
        addMouseListener(CenterMapListener(this))
        addMouseWheelListener(ZoomMouseWheelListenerCursor(this))
        addKeyListener(PanKeyListener(this))
        tileFactory = DefaultTileFactory(provider)
        this.zoom = mapStyle.zoom
    }

    fun focus(position: GeoPosition) {
        current = position
        addressLocation = current
    }

    fun points(name: String, positions: List<GeoPosition>, diameter: Double = 100.0, color: Color = BLUE) {
        val data = LinkedBlockingQueue<GeoPosition>(positions)
        val style = Style(fillColor = color, diameter = diameter)
        collection[name] = Points(name, positions = data, style = style)
    }

    fun diminish(name: String, positions: List<GeoPosition>, diameter: Double = 100.0, color: Color = BLUE) {
        val data = LinkedBlockingQueue<GeoPosition>(positions)
        val style = Style(fillColor = color, diameter = diameter)
        collection[name] = DiminishingPoints(name, positions = data, style = style)
    }

    fun line(name: String, positions: List<GeoPosition>, width: Float = 30F, color: Color = BLUE) {
        val data = LinkedBlockingQueue<GeoPosition>(positions)
        val style = Style(fillColor = color, strokeWidth = width)
        collection[name] = Line(name, positions = data, style = style)
    }

    fun draw() {
        overlayPainter = CompoundPainter(collection.values.toList())
    }
}
