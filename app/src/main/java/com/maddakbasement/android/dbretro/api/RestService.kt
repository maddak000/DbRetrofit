package com.maddakbasement.android.dbretro.api

import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RestService {
    @GET("companies")
    fun readCompanies(): Observable<List<Model.Company>>

    @GET("companies/5631d356f941f47900000110")
    fun readCompany(): Observable<Model.Company>

    @GET("questions/5e6d4abe33a9df6c000224e3")
    fun readQuestion():Observable<Model.Question>
}

object Model {
    data class Company(
        val _id: String,
        val name: String
    )

    data class Question(
        val question: String,
        val answers: List<String>
    )
}

class ServiceFactory private constructor(private val retrofit: Retrofit) {
    companion object {
        @Volatile
        private var INSTANCE: ServiceFactory? = null

        fun getInstance(baseUrl: String): ServiceFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: build(baseUrl).also { INSTANCE = it }
            }

        private fun build(baseUrl: String): ServiceFactory {
            val clientBuilder = OkHttpClient.Builder()
            val headerInterceptor = Interceptor {                   //it = chain
                val request = it.request().newBuilder()
                    .addHeader("x-apikey", "4e2989ea010abb27bb5c692dfb5d5b40a30d8")
                    .build()
                it.proceed(request)
            }

            val okHttpClient = clientBuilder.addInterceptor(headerInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()

            return ServiceFactory(retrofit)
        }
    }

    fun <T> build(service: Class<T>): T {
        return retrofit.create(service)
    }
}