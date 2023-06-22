package com.test.app.LoadMaps.CountryListPage

import com.test.app.LoadMaps.BasePresenter
import com.test.app.LoadMaps.BaseView
import com.test.app.LoadMaps.DataSets.CountriesApi

interface CountryListContract {
    interface view : BaseView {
        fun showProgress()
        fun hideProgress()
        fun onSuccessCountrylistLoaded(countriesList: ArrayList<CountriesApi?>?)
        fun onFailureCountryList(message: String?)
        fun onNoNetworkAvailable()
        fun showAlertDialog(title: String?, msg: String?)
        fun showCountryDetailsPage(commonName: String?)
    }

    interface presenter : BasePresenter {
        fun loadCountryList()
        fun redirectCountryDetails(commonName: String?)
    }
}