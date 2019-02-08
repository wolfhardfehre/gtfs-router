package nice.fontaine.gui.map.painters

import nice.fontaine.gui.map.styles.Style
import org.jxmapviewer.JXMapViewer
import org.jxmapviewer.viewer.GeoPosition
import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.util.*

class Line(name: String, positions: Queue<GeoPosition>, style: Style) : Form(name, positions, style) {

    override fun draw(g: Graphics2D, map: JXMapViewer) {
        var last: Point2D? = null
        positions.forEach {
            val pt = map.tileFactory.geoToPixel(it, map.zoom)
            if (last != null) g.drawLine(last!!.x.toInt(), last!!.y.toInt(), pt.x.toInt(), pt.y.toInt())
            last = pt
        }
    }
}
