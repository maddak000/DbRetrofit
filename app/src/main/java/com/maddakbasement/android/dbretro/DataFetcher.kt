package com.maddakbasement.android.dbretro

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maddakbasement.android.dbretro.api.RestDBApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "DataFetcher"

class DataFetcher {

    private var restDBApi: RestDBApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://rdb-simpledb.restdb.io/rest/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        restDBApi = retrofit.create(RestDBApi::class.java)
    }

    fun fetchData(): LiveData<String>{
        val responseLiveData: MutableLiveData<String> = MutableLiveData()
        val restDbRequest: Call<String> = restDBApi.fetchData()

        restDbRequest.enqueue(object: Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, "Failed to fetch data", t)
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d(TAG, "Response received")
                responseLiveData.value = response.body()
            }

        })
        return responseLiveData
    }
}