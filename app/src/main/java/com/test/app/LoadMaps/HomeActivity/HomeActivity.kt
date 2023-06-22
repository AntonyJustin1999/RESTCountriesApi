package com.test.app.LoadMaps.HomeActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.test.app.LoadMaps.CountryListPage.CountryListFragment
import com.test.app.LoadMaps.Data.AppDataRepository
import com.test.app.LoadMaps.Data.NetworkCallApiDataSource.INetworkApiCallDataSource
import com.test.app.LoadMaps.Data.NetworkCallApiDataSource.RetrofitApiCallDataSource
import com.test.app.R

class HomeActivity : AppCompatActivity(), HomeContract.view {

    private var context: Context? = null
    private var mPresenter: HomeContract.presenter? = null
    private var mNetworkApicall: INetworkApiCallDataSource? = null
    private var mAppDataRepository: AppDataRepository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        context = this
        mNetworkApicall = RetrofitApiCallDataSource(context)
        mAppDataRepository = AppDataRepository(mNetworkApicall)
        mPresenter = HomePresenter(this, mAppDataRepository)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(false)
        supportActionBar?.setTitle(R.string.countrylist)

        mPresenter?.redirectCountryListPage()
    }

    override fun showProgress() {}

    override fun hideProgress() {}

    override fun showCountryListPage() {
        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val mFragment = CountryListFragment(this)
        val mBundle = Bundle()
        mBundle.putString("mText", "TestData")
        mFragment.arguments = mBundle
        mFragmentTransaction.replace(R.id.layout_home_container, mFragment)
            .addToBackStack("CountryListFragment")
        mFragmentTransaction.commitAllowingStateLoss()
    }

    override fun showHomePage() {
        val intent = Intent(applicationContext, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        Log.e("Test", "onBackPressed - Called()")
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }
}