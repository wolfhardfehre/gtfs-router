package nice.fontaine.models.base

import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.JsonObject

val vbbTypes: JsonObject = jsonObject(
        "3" to jsonObject("type" to "BUS"),
        "100" to jsonObject("type" to "REGIONAL_TRAIN"),
        "109" to jsonObject("type" to "LIGHT_RAIL"),
        "400" to jsonObject("type" to "SUBWAY"),
        "700" to jsonObject("type" to "BUS"),
        "900" to jsonObject("type" to "TRAM"),
        "1000" to jsonObject("type" to "FERRY")
)
