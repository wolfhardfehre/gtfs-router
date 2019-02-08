package nice.fontaine.gui.map.styles

import nice.fontaine.gui.map.Mapbox

data class MapStyle(val tileStyle: Mapbox.Base = Mapbox.Base.LIGHT,
                    val zoom: Int = 7)
