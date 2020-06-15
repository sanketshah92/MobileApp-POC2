package com.sanket.mobileapp_poc2.datasource

import com.sanket.mobileapp_poc2.network.NetworkHelper

class UserRepository(private val apiHelper: NetworkHelper) {
    suspend fun getUsers(page: String) = apiHelper.getUsers(page)
}