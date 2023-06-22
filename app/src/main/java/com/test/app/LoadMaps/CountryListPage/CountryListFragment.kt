package com.test.app.LoadMaps.CountryListPage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.test.app.LoadMaps.CountryDetailsPage.CountryDetailsActivity
import com.test.app.LoadMaps.Data.AppDataRepository
import com.test.app.LoadMaps.Data.NetworkCallApiDataSource.INetworkApiCallDataSource
import com.test.app.LoadMaps.Data.NetworkCallApiDataSource.RetrofitApiCallDataSource
import com.test.app.LoadMaps.DataSets.CountriesApi
import com.test.app.R

class CountryListFragment(context:Context): Fragment(), CountryListContract.view {

    private var mCountryListView: View? = null
    private var context: Context? = null
    private var activity: Activity? = null
    private var animationLoader: LottieAnimationView? = null
    private var rv_country_list: RecyclerView? = null
    private var mRestaurantListAdapter: CountiresListAdapter? = null
    private var mNetworkApicall: INetworkApiCallDataSource? = null
    private var mAppDataRepository: AppDataRepository? = null
    private var layout_no_network: LinearLayout? = null

    init {
        this.context = context
    }

    // Override function when the view is being created
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflates the custom fragment layout
        mCountryListView = inflater.inflate(R.layout.fragment_country_list, container, false)
        val bundle = arguments
        val message = bundle!!.getString("mText")
        mNetworkApicall = RetrofitApiCallDataSource(context)
        mAppDataRepository = AppDataRepository(mNetworkApicall)
        mPresenter = CountryListPresenter(this, mAppDataRepository, context) as CountryListContract.presenter
        animationLoader = mCountryListView!!.findViewById(R.id.progress_bar)
        rv_country_list = mCountryListView!!.findViewById(R.id.rv_restaurant_list)
        layout_no_network = mCountryListView!!.findViewById(R.id.layout_no_network)

        mPresenter?.loadCountryList()

        return mCountryListView
    }

    override fun showProgress() {
        Log.e("Test","ShowProgressCalled")
        animationLoader!!.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        Log.e("Test","HideProgressCalled")
        animationLoader!!.visibility = View.GONE
    }

    override fun onSuccessCountrylistLoaded(countriesList: ArrayList<CountriesApi?>?) {
        Log.e("Test","onSuccess Called()")
        rv_country_list?.visibility = View.VISIBLE
        layout_no_network?.visibility = View.GONE
        val linearLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_country_list!!.layoutManager = linearLayoutManager
        mRestaurantListAdapter = CountiresListAdapter(countriesList,context)
        rv_country_list!!.adapter = mRestaurantListAdapter
    }

    override fun onFailureCountryList(message: String?) {
        Log.e("Test","onFailure Called()")
        rv_country_list?.visibility = View.VISIBLE
        layout_no_network?.visibility = View.GONE
        val countrieslist = ArrayList<CountriesApi?>()
        val linearLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_country_list!!.layoutManager = linearLayoutManager
        mRestaurantListAdapter = CountiresListAdapter(countrieslist,context)
        rv_country_list!!.adapter = mRestaurantListAdapter
    }

    override fun onNoNetworkAvailable() {
        rv_country_list?.visibility = View.GONE
        layout_no_network?.visibility = View.VISIBLE
    }

    override fun showAlertDialog(title: String?, msg: String?) {
        Log.e("Test","showAlertDialog Called()")
        showAlertDialogBox(title!!, msg!!)
    }

    override fun showCountryDetailsPage(commonName: String?) {
        Log.e("Test","showCountryDetailsPage Called()")
        val intent = Intent(context, CountryDetailsActivity::class.java)
        intent.putExtra("CountryName", commonName)
        //intent.putExtra("CountryName", "India")
        context?.startActivity(intent)
    }

    private fun showAlertDialogBox(title: String, msg: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setPositiveButton(R.string.yes
        ) { dialog, which -> dialog.dismiss() }
        builder.show()
    }

    class CountiresListAdapter(var mCountryList: ArrayList<CountriesApi?>?, var context:Context?) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return if (viewType == 2) {
                CountryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.country_details, parent, false))
            } else {
                EmptyViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.empty_view, parent, false)
                )
            }
            //            View view = LayoutInflater.from(activity).inflate(R.layout., parent, false);
//            return new ViewHolder(view);
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder.itemViewType == 2) {
                val countryViewHolder = holder as CountryViewHolder
                countryViewHolder.tv_common_name.text = mCountryList?.get(position)?.name?.common
                countryViewHolder.tv_official_name.text = mCountryList?.get(position)?.name?.official
                Glide.with(context!!).load(mCountryList?.get(position)?.flags?.png_url)
                    .into(countryViewHolder.iv_icon)
            } else {
                val emptyViewHolder = holder as EmptyViewHolder
                emptyViewHolder.tv_Empty.text = "No Data Found!!"
            }
        }

        override fun getItemViewType(position: Int): Int {
            return if (mCountryList != null) {
                if (mCountryList!!.size > 0) {
                    2
                } else {
                    1
                }
            } else {
                1
            }
        }

        override fun getItemCount(): Int {
            return if (mCountryList != null) {
                if (mCountryList!!.size > 0) {
                    mCountryList!!.size
                } else {
                    1
                }
            } else {
                1
            }
        }

        inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {
            var tv_common_name: TextView
            var tv_official_name: TextView
            var iv_icon: ImageView
            var layout_country_detail_holder: LinearLayout

            init {
                tv_common_name = itemView.findViewById<View>(R.id.tv_common_name) as TextView
                tv_official_name = itemView.findViewById<View>(R.id.tv_official_name) as TextView
                iv_icon = itemView.findViewById<ImageView>(R.id.iv_icon)
                layout_country_detail_holder =
                    itemView.findViewById<LinearLayout>(R.id.layout_country_detail_holder)
                layout_country_detail_holder.setOnClickListener(this)
            }

            override fun onClick(v: View) {
                if (v.id == R.id.layout_country_detail_holder) {
                    mPresenter?.redirectCountryDetails(mCountryList?.get(adapterPosition)?.name?.common)
                }
            }
        }

        inner class EmptyViewHolder internal constructor(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
            val tv_Empty: TextView

            init {
                tv_Empty = itemView.findViewById<TextView>(R.id.tv_empty)
            }
        }
    }

    companion object{
        private var mPresenter: CountryListContract.presenter? = null
    }

}