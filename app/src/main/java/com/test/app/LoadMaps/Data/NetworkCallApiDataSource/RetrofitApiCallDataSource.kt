package com.test.app.LoadMaps.Data.NetworkCallApiDataSource

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.test.app.LoadMaps.API.ApiCall
import com.test.app.LoadMaps.DataSets.CountriesApi
import com.test.app.LoadMaps.DataSets.CountryDetailsApi
import com.test.app.LoadMaps.DataSets.NetworkResponseData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApiCallDataSource(context: Context?):INetworkApiCallDataSource {
    private val BaseUrl = "https://restcountries.com/"
    private var context: Context? = null
    //private BasePresenter mPresenter;

    init {
        this.context = context
    }

    override fun loadCountrylist(listener: INetworkApiCallDataSource.OnFinishedListener?) {
        try {
            var retrofit: Retrofit? = null
            val httpClient = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.cache(null)
            httpClient.addInterceptor(logging)
            val gson = GsonBuilder()
                .setLenient()
                .create()
            if (retrofit == null) {
                val apiUrl = BaseUrl
                retrofit = Retrofit.Builder()
                    .baseUrl(apiUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build()
            }
            val apiCall: ApiCall = retrofit!!.create(ApiCall::class.java)
            val Call: Call<ArrayList<CountriesApi?>?>? = apiCall.getAllCountrieslist(
                true, "" + "name,flags")
            Call?.enqueue(object : Callback<ArrayList<CountriesApi?>?> {
                override fun onResponse(call: Call<ArrayList<CountriesApi?>?>, response: Response<ArrayList<CountriesApi?>?>) {
                    try {
                        if (response.body() != null) {
                            val responseData = NetworkResponseData()
                            responseData.countryList = response.body()
                            listener?.onSuccess(responseData)
                        } else {
                            listener?.onFailure("No Data Found")
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        listener?.onFailure(e.message)
                    }
                }

                override fun onFailure(call: Call<ArrayList<CountriesApi?>?>, t: Throwable) {
                    listener?.onFailure(t.message)
                }
            })
        } catch (e: Exception) {
            Log.e("Test", "Exception ==> " + e.message)
            listener?.onFailure(e.message)
        }
    }

    override fun loadCountrydetails(countryName: String?, listener: INetworkApiCallDataSource.OnFinishedListener?) {
        try {
            Log.e("Test", "loadCountrydetails Called()")
            var retrofit: Retrofit? = null
            val httpClient = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.cache(null)
            httpClient.addInterceptor(logging)
            val gson = GsonBuilder()
                .setLenient()
                .create()
            if (retrofit == null) {
                val apiUrl = BaseUrl
                retrofit = Retrofit.Builder()
                    .baseUrl(apiUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build()
            }
            val apiCall: ApiCall = retrofit!!.create(ApiCall::class.java)
            val Call: Call<ArrayList<CountryDetailsApi?>?>? = apiCall.getCountryInfo(countryName)
            Call?.enqueue(object : Callback<ArrayList<CountryDetailsApi?>?> {
                override fun onResponse(
                    call: Call<ArrayList<CountryDetailsApi?>?>,
                    response: Response<ArrayList<CountryDetailsApi?>?>
                ) {
                    try {
                        Log.e("Test", "loadCountrydetails OnResponse Called()")
                        if (response.body() != null) {
                            if (response.body() != null) {
                                if (response.body()!!.size > 0) {
                                    val responseData = NetworkResponseData()
                                    responseData.countriesDetails = response.body()
                                    listener?.onSuccess(responseData)
                                } else {
                                    listener?.onFailure("No data")
                                }
                            } else {
                                listener?.onFailure("Something error")
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        listener?.onFailure(e.message)
                    }
                }

                override fun onFailure(call: Call<ArrayList<CountryDetailsApi?>?>, t: Throwable) {
                    Log.e("Test", "Exception => " + t.message)
                    listener?.onFailure(t.message)
                }
            })
        } catch (e: Exception) {
            Log.e("Test", "Exception ==> " + e.message)
            listener?.onFailure(e.message)
        }
    }
}