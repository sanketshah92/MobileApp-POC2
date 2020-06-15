package com.sanket.mobileapp_poc2.network

import com.sanket.mobileapp_poc2.model.UserData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUsers(
        @Query("per_page") per_page: String,
        @Query("page") page: String
    ): UserData


}