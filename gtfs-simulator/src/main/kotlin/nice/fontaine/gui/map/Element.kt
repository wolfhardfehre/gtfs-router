package nice.fontaine.gui.map

import java.awt.Color

val WHITE = Color(255, 255, 255, 150)
val GOLD = Color(255, 215, 0, 150)
val ORANGE = Color(254, 178, 76, 150)
val ORANGERED = Color(255, 69, 0, 150)
val RED = Color(222, 45, 38, 150)
val VIOLETT = Color(197, 27, 138, 150)
val PINK = Color(255, 105, 180, 150)
val BLUE = Color(33, 135, 253, 150)
val GREEN = Color(5, 221, 127, 100)
val CHARTREUSE = Color(127, 255, 0, 100)

sealed class Element(val color: Color, val diameter: Double, val width: Float) {
    class Point(c: Color, d: Double) : Element(c, d, 0F)
    class Line(c: Color, w: Float) : Element(c, 0.0, w)
    class Diminish(c: Color, d: Double) : Element(c, d, 0F)
}
