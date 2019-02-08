package nice.fontaine.gui.map.styles

import nice.fontaine.gui.map.BLUE
import nice.fontaine.gui.map.painters.ALL
import java.awt.Color

data class Style(val fillColor: Color = BLUE,
                 val strokeWidth: Float = 2F,
                 val diameter: Double = 10.0,
                 var limit: Int = ALL)
