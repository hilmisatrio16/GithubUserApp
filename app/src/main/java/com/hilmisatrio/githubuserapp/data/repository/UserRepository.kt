package com.hilmisatrio.githubuserapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.hilmisatrio.githubuserapp.data.Result
import com.hilmisatrio.githubuserapp.data.local.entity.UserFavorite
import com.hilmisatrio.githubuserapp.data.local.room.FavoriteDao
import com.hilmisatrio.githubuserapp.data.remote.response.ResponseDetailUser
import com.hilmisatrio.githubuserapp.data.remote.retrofit.ApiService
import com.hilmisatrio.githubuserapp.data.remote.response.ResponseFollow
import com.hilmisatrio.githubuserapp.data.remote.response.ResponseSearchUsers

class UserRepository private constructor(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao
) {
    fun getDetail(username: String): LiveData<Result<ResponseDetailUser>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailUser(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollowers(username: String): LiveData<Result<ResponseFollow>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDataFollowers(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollowing(username: String): LiveData<Result<ResponseFollow>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDataFollowing(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun searchUser(username: String): LiveData<Result<ResponseSearchUsers>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUsers(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFavorit(): LiveData<List<UserFavorite>> {
        return favoriteDao.getUserFavorite()
    }

    fun checkFavorite(username: String): LiveData<UserFavorite> {
        return favoriteDao.checkUser(username)
    }

    suspend fun insertUserFavorite(dataUser: UserFavorite) {
        favoriteDao.insertUserFavorite(dataUser)
    }

    suspend fun deleteUserFavorite(dataUser: UserFavorite) {
        favoriteDao.deleteUserFavorite(dataUser)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteDao: FavoriteDao
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, favoriteDao)
            }.also { instance = it }
    }

}