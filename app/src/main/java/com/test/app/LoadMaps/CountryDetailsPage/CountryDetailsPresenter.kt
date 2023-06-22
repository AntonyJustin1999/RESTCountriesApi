package com.test.app.LoadMaps.CountryDetailsPage

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.test.app.LoadMaps.Data.AppDataRepository
import com.test.app.LoadMaps.Data.NetworkCallApiDataSource.INetworkApiCallDataSource
import com.test.app.LoadMaps.DataSets.NetworkResponseData

class CountryDetailsPresenter(mCountryDetailsView: CountryDetailsContract.view?,
                              mAppDataRepository: AppDataRepository?,
                              context: Context?): CountryDetailsContract.presenter,
    INetworkApiCallDataSource.OnFinishedListener {
    private var mCountryDetailsView: CountryDetailsContract.view? = null
    private var mAppDataRepository: AppDataRepository? = null
    private var context: Context? = null

    init {
        this.mCountryDetailsView = mCountryDetailsView
        this.mAppDataRepository = mAppDataRepository
        this.context = context
    }

    private fun isNetworkAvailable(): Boolean {
        return try {
            if (context != null) {
                val connectivity = context!!
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                if (connectivity != null) {
                    val info = connectivity.activeNetworkInfo
                    if (info != null && info.isConnected) {
                        return true
                    }
                }
            } else {
                Log.e("Test", "isNetworkAvailable context is null")
            }
            false
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    override fun onSuccess(responseData: NetworkResponseData?) {
        Log.e("Test", "CountryDetailsPresenter onSuccess Called()")
        mCountryDetailsView?.hideProgress()
        mCountryDetailsView?.onSuccessCountrydetailsLoaded(responseData?.countriesDetails)
    }

    override fun onFailure(msg: String?) {
        Log.e("Test", "CountryDetailsPresenter onFailure Called()")
        mCountryDetailsView?.hideProgress()
        mCountryDetailsView?.onFailureCountryDetails(msg)
    }

    override fun loadCountryDetails(name: String?) {
        Log.e("Test", "CountryDetailsPresenter loadCountryDetails Called()")
        mCountryDetailsView?.showProgress()
        if (isNetworkAvailable()) {
            Log.e("Test", "CountryDetailsPresenter network true")
            mAppDataRepository!!.loadCountrydetails(name, this)
        } else {
            Log.e("Test", "CountryDetailsPresenter network false")
            mCountryDetailsView?.hideProgress()
            mCountryDetailsView?.onNoNetworkConnection()
        }
    }

    override fun redirectCountryList() {
        mCountryDetailsView?.showCountryListPage()
    }
}