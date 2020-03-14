package com.maddakbasement.android.dbretro.api

import retrofit2.http.GET

interface RestService {
    @GET("companies")
    fun readCompanies(): List<Model.Company>

    @GET("companies/5631d356f941f47900000110")
    fun readCompany(): Model.Company
}

object Model {
    data class Company(
        val _id: String,
        val name: String
    )
}