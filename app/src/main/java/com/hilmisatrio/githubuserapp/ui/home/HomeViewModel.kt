package com.hilmisatrio.githubuserapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hilmisatrio.githubuserapp.data.repository.UserRepository
import com.hilmisatrio.githubuserapp.data.Result
import com.hilmisatrio.githubuserapp.data.remote.response.ResponseSearchUsers

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    var _dataSearch: MutableLiveData<String> = MutableLiveData()
    val dataSearch: LiveData<String> get() = _dataSearch

    fun getUsers(username: String): LiveData<Result<ResponseSearchUsers>> =
        userRepository.searchUser(username)
}