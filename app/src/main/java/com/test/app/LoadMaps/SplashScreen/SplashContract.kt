package com.test.app.LoadMaps.SplashScreen

interface SplashContract {
    interface view {
        fun showProgressBar()
        fun hideProgressBar()
        //fun redirectToAccount()
        fun redirectToHome()
    }

    interface presenter {
        fun redirect()
    }
}