package com.hilmisatrio.githubuserapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hilmisatrio.githubuserapp.data.local.entity.UserFavorite
import com.hilmisatrio.githubuserapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getDetail(username: String) = userRepository.getDetail(username)

    fun getListFollowers(username: String) = userRepository.getFollowers(username)

    fun getListFollowing(username: String) = userRepository.getFollowing(username)

    fun checkFavoriteUser(username: String) = userRepository.checkFavorite(username)

    fun addFavorite(dataUser: UserFavorite) {
        viewModelScope.launch {
            userRepository.insertUserFavorite(dataUser)
        }
    }

    fun deleteFavorite(dataUser: UserFavorite) {
        viewModelScope.launch {
            userRepository.deleteUserFavorite(dataUser)
        }
    }
}