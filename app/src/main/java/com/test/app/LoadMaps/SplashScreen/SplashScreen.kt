package com.test.app.LoadMaps.SplashScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.test.app.LoadMaps.Data.AppDataRepository
import com.test.app.LoadMaps.Data.NetworkCallApiDataSource.INetworkApiCallDataSource
import com.test.app.LoadMaps.Data.NetworkCallApiDataSource.RetrofitApiCallDataSource
import com.test.app.LoadMaps.HomeActivity.HomeActivity
import com.test.app.R

class SplashScreen : AppCompatActivity(),SplashContract.view {

    private var context: Context? = null

    private var mPresenter: SplashContract.presenter? = null
    private var mNetworkApicall: INetworkApiCallDataSource? = null
    private var mAppDataRepository: AppDataRepository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        context = this
        mNetworkApicall = RetrofitApiCallDataSource(context)
        mAppDataRepository = AppDataRepository(mNetworkApicall)
        mPresenter = SplashPresenter(this, mAppDataRepository)
        //getSupportActionBar().hide();

        // This method is used so that your splash activity can cover the entire screen.
        //getSupportActionBar().hide();

        // This method is used so that your splash activity can cover the entire screen.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        mPresenter?.redirect()

    }

    override fun showProgressBar() {
        TODO("Not yet implemented")
    }

    override fun hideProgressBar() {
        TODO("Not yet implemented")
    }

//    override fun redirectToAccount() {
//        val intent = Intent(applicationContext, AccountActivity::class.java)
//        startActivity(intent)
//        finish()
//    }

    override fun redirectToHome() {
        val intent = Intent(applicationContext, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}