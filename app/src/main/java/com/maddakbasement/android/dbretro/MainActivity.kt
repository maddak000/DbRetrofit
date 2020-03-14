package com.maddakbasement.android.dbretro

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "restDBrequest RESULT"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://rdb-simpledb.restdb.io/rest/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
        val restDBApi: RestDBApi = retrofit.create(RestDBApi::class.java)

        val restDBrequest: Call<String> = restDBApi.fetchContents()

        restDBrequest.enqueue(object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, "Failed to fetch photos", t)
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d(TAG, "Response received: ${response.body()}")
                showResult(response.body() ?: "FAILED")
            }
        })
    }

    private fun showResult(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
