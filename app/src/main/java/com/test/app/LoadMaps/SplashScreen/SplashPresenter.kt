package com.test.app.LoadMaps.SplashScreen

import android.os.Handler
import com.test.app.LoadMaps.Data.AppDataRepository

class SplashPresenter(mSplashView: SplashContract.view?,
                      mAppDataRepository: AppDataRepository?): SplashContract.presenter {
    private var mSplashView: SplashContract.view? = null
    private var mAppDataRepository: AppDataRepository? = null

    init {
        this.mSplashView = mSplashView
        this.mAppDataRepository = mAppDataRepository
    }

    override fun redirect() {
        Handler().postDelayed({ //Test
            mSplashView!!.redirectToHome()
        }, 2000)
    }
}