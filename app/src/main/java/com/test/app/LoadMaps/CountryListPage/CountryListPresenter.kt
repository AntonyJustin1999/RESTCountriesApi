package com.test.app.LoadMaps.CountryListPage

import android.content.Context
import android.net.ConnectivityManager
import com.test.app.LoadMaps.Data.AppDataRepository
import com.test.app.LoadMaps.Data.NetworkCallApiDataSource.INetworkApiCallDataSource
import com.test.app.LoadMaps.DataSets.NetworkResponseData

class CountryListPresenter(mCountryListView: CountryListContract.view?,
                           mAppDataRepository: AppDataRepository?,
                           context: Context?) : CountryListContract.presenter,
    INetworkApiCallDataSource.OnFinishedListener {
    private var mCountryListView: CountryListContract.view? = null
    private var mAppDataRepository: AppDataRepository? = null
    private var context: Context? = null

    init {
        this.mCountryListView = mCountryListView
        this.mAppDataRepository = mAppDataRepository
        this.context = context
    }

    override fun loadCountryList() {
        mCountryListView!!.showProgress()
        if (isNetworkAvailable()) {
            mAppDataRepository!!.loadCountrylist(this)
        } else {
            mCountryListView?.hideProgress()
            mCountryListView?.onNoNetworkAvailable()
        }
    }

    override fun redirectCountryDetails(commonName: String?) {
        mCountryListView!!.showCountryDetailsPage(commonName)
    }

    private fun isNetworkAvailable(): Boolean {
        return try {
            if (context != null) {
                val connectivity = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                if (connectivity != null) {
                    val info = connectivity.activeNetworkInfo
                    if (info != null && info.isConnected) {
                        return true
                    }
                }
            }
            false
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    override fun onSuccess(responseData: NetworkResponseData?) {
        mCountryListView!!.hideProgress()
        mCountryListView?.onSuccessCountrylistLoaded(responseData?.countryList)
    }

    override fun onFailure(msg: String?) {
        mCountryListView!!.hideProgress()
        mCountryListView?.onFailureCountryList(msg)
    }
}