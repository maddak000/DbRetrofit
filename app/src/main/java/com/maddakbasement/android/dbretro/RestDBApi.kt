package com.maddakbasement.android.dbretro

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface RestDBApi {
    @Headers("x-apikey: 560bd47058e7ab1b2648f4e7")
    @GET("companies")
    fun fetchContents(): Call<String>
    
}

//        connection.setRequestProperty("User-Agent", "my-restdb-app");
//        connection.setRequestProperty("Accept", "application/json");
//        connection.setRequestProperty("x-apikey", "560bd47058e7ab1b2648f4e7");