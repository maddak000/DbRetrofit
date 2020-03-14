package com.maddakbasement.android.dbretro

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

private const val TAG = "restDBrequest RESULT"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val restDbLiveData: LiveData<String> = DataFetcher().fetchData()
        restDbLiveData.observe(
            this, Observer { responseString ->
                Log.d(TAG, "Response received: $responseString")
            }
        )
    }

    private fun showResult(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
