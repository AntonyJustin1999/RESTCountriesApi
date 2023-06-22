package com.test.app.LoadMaps.CountryDetailsPage

import com.test.app.LoadMaps.BasePresenter
import com.test.app.LoadMaps.BaseView
import com.test.app.LoadMaps.DataSets.CountryDetailsApi

interface CountryDetailsContract {
    interface view : BaseView {
        fun showProgress()
        fun hideProgress()
        fun onSuccessCountrydetailsLoaded(countriesList: ArrayList<CountryDetailsApi?>?)
        fun onFailureCountryDetails(message: String?)
        fun showAlertDialog(title: String?, msg: String?)
        fun showCountryListPage()
        fun onNoNetworkConnection()
    }

    interface presenter : BasePresenter {
        fun loadCountryDetails(name: String?)
        fun redirectCountryList()
    }
}