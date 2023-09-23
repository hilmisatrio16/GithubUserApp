package com.hilmisatrio.githubuserapp.di

import android.content.Context
import com.hilmisatrio.githubuserapp.data.local.room.FavoriteDatabase
import com.hilmisatrio.githubuserapp.data.remote.retrofit.ApiConfig
import com.hilmisatrio.githubuserapp.data.repository.UserRepository

object ServerLocator {
    fun provideUserRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteDatabase.getInstance(context)
        val favoriteDao = database.favoriteDao()
        return UserRepository.getInstance(apiService, favoriteDao)
    }

}