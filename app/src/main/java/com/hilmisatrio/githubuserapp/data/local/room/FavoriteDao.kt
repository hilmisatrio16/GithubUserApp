package com.hilmisatrio.githubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hilmisatrio.githubuserapp.data.local.entity.UserFavorite

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM UserFavorite ORDER BY username ASC")
    fun getUserFavorite(): LiveData<List<UserFavorite>>

    @Query("SELECT * FROM UserFavorite WHERE username = :username ")
    fun checkUser(username: String): LiveData<UserFavorite>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserFavorite(user: UserFavorite)

    @Delete
    suspend fun deleteUserFavorite(user: UserFavorite)


}