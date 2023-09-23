package com.hilmisatrio.githubuserapp.ui.favorite

import androidx.lifecycle.ViewModel
import com.hilmisatrio.githubuserapp.data.repository.UserRepository

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUsersFavorite() = userRepository.getFavorit()

}