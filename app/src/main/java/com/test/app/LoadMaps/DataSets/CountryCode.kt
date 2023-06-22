package com.test.app.LoadMaps.DataSets

import com.google.gson.annotations.SerializedName

class CountryCode {

    @SerializedName("root")
    var root: String? = null
    @SerializedName("suffixes")
    var suffixes: ArrayList<String>? = null

}