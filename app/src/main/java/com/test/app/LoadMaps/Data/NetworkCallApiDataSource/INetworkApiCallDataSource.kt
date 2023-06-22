package com.test.app.LoadMaps.Data.NetworkCallApiDataSource

import com.test.app.LoadMaps.DataSets.NetworkResponseData

interface INetworkApiCallDataSource {
    interface OnFinishedListener {
        fun onSuccess(responseData: NetworkResponseData?)
        fun onFailure(msg: String?)
    }

    fun loadCountrylist(listener: OnFinishedListener?)
    fun loadCountrydetails(countryName: String?, listener: OnFinishedListener?)
}