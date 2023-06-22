package com.test.app.LoadMaps.HomeActivity

interface HomeContract {
    interface view {
        fun showProgress()
        fun hideProgress()
        fun showCountryListPage()
        fun showHomePage()
        //fun showLoginPage()
    }

    interface presenter {
        fun redirectCountryListPage()
        fun reloadHomePage()
        //fun logOut()
    }
}