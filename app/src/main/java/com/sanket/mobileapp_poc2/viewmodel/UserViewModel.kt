package com.sanket.mobileapp_poc2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanket.mobileapp_poc2.datasource.UserRepository
import com.sanket.mobileapp_poc2.model.UserData
import com.sanket.mobileapp_poc2.network.Resource
import com.sanket.mobileapp_poc2.ui.INITIAL_PAGE
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserViewModel(val repository: UserRepository) : ViewModel() {
    val users: MutableLiveData<Resource<UserData>> = MutableLiveData()
    private lateinit var userData: UserData


    fun getUsers(page: Int) {
        users.value = Resource.loading(data = null)
        viewModelScope.launch {
            async {
                userData = repository.getUsers("$page")
            }.await()
        }.invokeOnCompletion {
            if (it != null) {
                users.value =
                    Resource.error(data = null, message = "Something went wrong, Try later !")
            } else {
                users.value = Resource.success(data = userData)
            }
        }
    }
}