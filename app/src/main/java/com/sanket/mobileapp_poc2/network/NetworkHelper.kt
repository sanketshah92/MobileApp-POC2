package com.sanket.mobileapp_poc2.network

class NetworkHelper(private val apiService: ApiService) {


    suspend fun getUsers(page: String) =
        apiService.getUsers(page = page, per_page = "5")


}