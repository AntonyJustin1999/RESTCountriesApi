package com.test.app.LoadMaps.CountryDetailsPage

import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.test.app.LoadMaps.Data.AppDataRepository
import com.test.app.LoadMaps.Data.NetworkCallApiDataSource.INetworkApiCallDataSource
import com.test.app.LoadMaps.Data.NetworkCallApiDataSource.RetrofitApiCallDataSource
import com.test.app.LoadMaps.DataSets.CountryDetailsApi
import com.test.app.R
import java.util.zip.GZIPOutputStream


class CountryDetailsActivity : AppCompatActivity(),CountryDetailsContract.view, AdapterView.OnItemSelectedListener {

    private var progress_bar: LottieAnimationView? = null
    private var context: Context? = null

    private var tv_common_name: TextView? = null
    private  var tv_official_name:TextView? = null
    private  var tv_currency:TextView? = null
    private  var tv_capital:TextView? = null
    private  var tv_country_code:TextView? = null
    private  var tv_currency_code:TextView? = null
    private  var sv_language_list: Spinner? = null
    private var iv_flag: ImageView? = null
    private var layout_content_holder: LinearLayout? = null
    private var layout_empty_holder: RelativeLayout? = null
    private var countryName = ""
    private var mPresenter: CountryDetailsContract.presenter? = null
    private var mNetworkApicall: INetworkApiCallDataSource? = null
    private var mAppDataRepository: AppDataRepository? = null
    private var layout_no_network: LinearLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.country_detail_activity)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(false)
        supportActionBar?.setTitle(R.string.countrydetails)

        val intent = intent
        countryName = intent.getStringExtra("CountryName")!!

        context = this

        mNetworkApicall = RetrofitApiCallDataSource(context)
        mAppDataRepository = AppDataRepository(mNetworkApicall)
        mPresenter = CountryDetailsPresenter(this, mAppDataRepository, context)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        progress_bar = findViewById(R.id.progress_bar)
        iv_flag = findViewById(R.id.iv_flag)
        tv_common_name = findViewById(R.id.tv_common_name)
        tv_official_name = findViewById(R.id.tv_official_name)
        tv_currency = findViewById(R.id.tv_currency)
        tv_capital = findViewById(R.id.tv_capital)
        tv_country_code = findViewById(R.id.tv_country_code)
        tv_currency_code = findViewById(R.id.tv_currency_code)
        sv_language_list = findViewById(R.id.language_list)
        layout_content_holder = findViewById(R.id.layout_content_holder)
        layout_empty_holder = findViewById(R.id.layout_empty_holder)
        layout_empty_holder?.visibility = View.GONE
        layout_no_network = findViewById(R.id.layout_no_network)
        layout_no_network?.visibility = View.GONE

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        Log.e("Test", "CountryDetailsActivity Called()")

        sv_language_list?.getBackground()?.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        mPresenter?.loadCountryDetails(countryName)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            mPresenter!!.redirectCountryList()
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

    override fun showProgress() {
        progress_bar?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_bar?.visibility = View.GONE
    }

    override fun onSuccessCountrydetailsLoaded(countriesList: ArrayList<CountryDetailsApi?>?) {

        layout_empty_holder?.visibility = View.GONE
        layout_content_holder?.visibility = View.VISIBLE
        layout_no_network?.visibility = View.GONE

        //Language List
        var languageList:ArrayList<String> = ArrayList<String>()
        for ((key, value) in countriesList?.get(0)?.languages!!) {
            languageList.add(value)
        }

        val dataAdapter = ArrayAdapter(this, R.layout.row_spinner_language, languageList)
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sv_language_list?.setAdapter(dataAdapter)

        //Currency
        var currency:String = ""
        var currency_name:String = ""

        for ((key, value) in countriesList?.get(0)?.currencies!!) {
            currency = key +" (${value.symbol})"
            currency_name = value.name?:""
        }
        tv_currency?.setText(currency_name)
        tv_currency_code?.setText(currency)

        //CountryCode
        var countryCode:String = ""
        countryCode =countriesList?.get(0)?.countryCode?.root + countriesList?.get(0)?.countryCode?.suffixes?.get(0)
        tv_country_code?.setText(countryCode)

        var temp = StringBuilder()
        Glide.with(context!!).load(countriesList?.get(0)?.coatOfArms?.png_url).placeholder(R.drawable.baseline_image_24).into(iv_flag!!)
        tv_common_name!!.text = countriesList?.get(0)?.name?.common
        tv_official_name!!.text = countriesList?.get(0)?.name?.official
        temp = StringBuilder("")
        for (i in countriesList?.get(0)?.capital!!.indices) {
            temp.append(countriesList?.get(0)!!.capital!![i])
        }
        tv_capital!!.text = temp
    }


    override fun onFailureCountryDetails(message: String?) {
        Log.e("Test", "onFailureCountryDetails Called()")
        layout_empty_holder!!.visibility = View.VISIBLE
        layout_content_holder!!.visibility = View.GONE
        layout_no_network!!.visibility = View.GONE
    }

    override fun showAlertDialog(title: String?, msg: String?) {
        ShowAlertDialog(title, msg)
    }

    override fun showCountryListPage() {
        finish()
    }

    override fun onNoNetworkConnection() {
        layout_empty_holder!!.visibility = View.GONE
        layout_content_holder!!.visibility = View.GONE
        layout_no_network!!.visibility = View.VISIBLE
    }

    fun ShowAlertDialog(title: String?, msg: String?) {
        val builder = AlertDialog.Builder(
            context!!
        )
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setPositiveButton(
            android.R.string.yes
        ) { dialog, which -> dialog.dismiss() }
        builder.show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val item = parent?.getItemAtPosition(position).toString()
        // Showing selected spinner item
        Toast.makeText(parent?.context, "Selected: $item", Toast.LENGTH_LONG).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(parent?.context, "Nothing has Selected", Toast.LENGTH_LONG).show()
    }

}