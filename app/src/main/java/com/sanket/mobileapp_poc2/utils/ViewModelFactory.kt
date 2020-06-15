package com.sanket.mobileapp_poc2.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sanket.mobileapp_poc2.datasource.UserRepository
import com.sanket.mobileapp_poc2.network.NetworkHelper
import com.sanket.mobileapp_poc2.viewmodel.UserViewModel

class ViewModelFactory(private val apiHelper: NetworkHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(UserRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}