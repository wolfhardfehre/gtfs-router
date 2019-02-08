package nice.fontaine.gui.map.painters

import nice.fontaine.gui.map.styles.Style
import org.jxmapviewer.JXMapViewer
import org.jxmapviewer.viewer.GeoPosition
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import java.util.*

class Points(name: String, positions: Queue<GeoPosition>, style: Style) : Form(name, positions, style) {

    override fun draw(g: Graphics2D, map: JXMapViewer) {
        positions.forEach {
            val meterPerPixel = meterPerPixel(it.latitude, MAX_ZOOM - map.zoom)
            val pixel = style.diameter / meterPerPixel
            val halfPixel = pixel / 2
            val pt = map.tileFactory.geoToPixel(it, map.zoom)
            val shape = Ellipse2D.Double(pt.x - halfPixel, pt.y - halfPixel, pixel, pixel)
            g.fill(shape)
            g.draw(shape)
        }
    }
}
