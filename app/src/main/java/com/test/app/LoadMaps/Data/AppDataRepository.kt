package com.test.app.LoadMaps.Data

import com.test.app.LoadMaps.Data.NetworkCallApiDataSource.INetworkApiCallDataSource
import com.test.app.LoadMaps.Data.NetworkCallApiDataSource.INetworkApiCallDataSource.OnFinishedListener

class AppDataRepository(networkApiCallDataSource: INetworkApiCallDataSource?) : INetworkApiCallDataSource {

    private var networkApiCallDataSource: INetworkApiCallDataSource? = null
    init {
        this.networkApiCallDataSource = networkApiCallDataSource
    }

    override fun loadCountrylist(listener: OnFinishedListener?) {
        networkApiCallDataSource!!.loadCountrylist(listener)
    }

    override fun loadCountrydetails(countryName: String?, listener: OnFinishedListener?) {
        networkApiCallDataSource!!.loadCountrydetails(countryName, listener)
    }


}