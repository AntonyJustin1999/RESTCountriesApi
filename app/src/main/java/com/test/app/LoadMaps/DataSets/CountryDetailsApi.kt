package com.test.app.LoadMaps.DataSets

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

class CountryDetailsApi {
    @SerializedName("flags")
    var flags: Flags? = null

    @SerializedName("coatOfArms")
    var coatOfArms: CoatOfArms? = null

    @SerializedName("name")
    var name: Name? = null

    @SerializedName("startOfWeek")
    var startOfWeek: String? = null

    @SerializedName("currencies")
    val currencies: Map<String, CurrencyData> = emptyMap()

    @SerializedName("capital")
    var capital: ArrayList<String>? = null

    @SerializedName("languages")
    var languages: Map<String, String> = emptyMap()

    @SerializedName("latlng")
    var latLng: ArrayList<Float>? = null

    @SerializedName("idd")
    var countryCode: CountryCode? = null

    @SerializedName("area")
    var area: Double? = null

    @SerializedName("population")
    var population: Long? = null

    @SerializedName("continents")
    var Continents: ArrayList<String>? = null
}