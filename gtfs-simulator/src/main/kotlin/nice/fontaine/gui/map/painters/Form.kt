package nice.fontaine.gui.map.painters

import nice.fontaine.gui.map.styles.Style
import org.jxmapviewer.JXMapViewer
import org.jxmapviewer.painter.Painter
import org.jxmapviewer.viewer.GeoPosition
import java.awt.BasicStroke
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.util.*

const val ALL = -1
const val MAX_ZOOM = 19
const val CIRCUMFERENCE_METERS = 40075017.0

abstract class Form(val name: String,
                    protected val positions: Queue<GeoPosition>,
                    protected val style: Style)
    : Painter<JXMapViewer> {

    init {
        style.limit = if (style.limit <= ALL) positions.size else style.limit
        while (positions.size > style.limit) {
            positions.remove()
        }
    }

    override fun paint(graphics2D: Graphics2D, map: JXMapViewer, w: Int, h: Int) {
        val graphics = graphics2D.create() as Graphics2D
        val rect = map.viewportBounds
        graphics.translate(-rect.x, -rect.y)
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        graphics.color = style.fillColor
        graphics.stroke = BasicStroke(style.strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
        draw(graphics, map)
        graphics.dispose()
    }

    protected fun meterPerPixel(lat: Double, zoom: Int): Double =
            CIRCUMFERENCE_METERS * Math.abs(Math.cos(lat)) / Math.pow(2.0, (zoom + 8.0))

    protected abstract fun draw(g: Graphics2D, map: JXMapViewer)
}
