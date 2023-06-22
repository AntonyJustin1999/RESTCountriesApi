package com.test.app.LoadMaps.HomeActivity

import com.test.app.LoadMaps.Data.AppDataRepository

class HomePresenter(mHomeView: HomeContract.view?, mAppDataRepository: AppDataRepository?): HomeContract.presenter {
    private var mHomeView: HomeContract.view? = null
    private var mAppDataRepository: AppDataRepository? = null

    init {
        this.mHomeView = mHomeView
        this.mAppDataRepository = mAppDataRepository
    }

    override fun redirectCountryListPage() {
        mHomeView?.showCountryListPage()
    }

    override fun reloadHomePage() {
        mHomeView?.showHomePage()
    }

}