package com.maddakbasement.android.dbretro

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.maddakbasement.android.dbretro.api.RestService
import com.maddakbasement.android.dbretro.api.ServiceFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

private const val TAG = "restDBrequest RESULT"

class MainActivity : AppCompatActivity() {

    private val service by lazy {
        val factory = ServiceFactory.getInstance("https://marcintest-5b77.restdb.io/rest/")
        factory.build(RestService::class.java)
    }
    private var disposable: Disposable? = null
    private lateinit var requestButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestButton = findViewById(R.id.button)
        requestButton.setOnClickListener {
            getQuestions()
        }
    }

    fun fetchLiveDataCompanies() {
        val restDbLiveData: LiveData<String> = DataFetcher().fetchData()
        restDbLiveData.observe(
            this, Observer { responseString ->
                Log.d(TAG, "Response received: $responseString")
            }
        )
    }

    fun getCompany() {
        this.disposable = this.service.readCompany()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ company ->
                run {
                    showResult(company.name)
                }
            },
                { showResult("Failed to read the product") })
    }

    fun getQuestions() {
        this.disposable = this.service.readQuestion()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ question ->
                run {
                    showResult(question.question)
                }
            },
                { showResult("Failed to read the product") })
    }

    fun showResult(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
