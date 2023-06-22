package com.test.app.LoadMaps.DataSets

import com.google.gson.annotations.SerializedName

class CountriesApi {
    @SerializedName("flags")
    var flags: Flags? = null

    @SerializedName("name")
    var name: Name? = null
}