package com.hilmisatrio.githubuserapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hilmisatrio.githubuserapp.data.local.entity.UserFavorite

@Database(entities = [UserFavorite::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var instance: FavoriteDatabase? = null
        fun getInstance(context: Context): FavoriteDatabase =
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                FavoriteDatabase::class.java,
                "Favorite.db"
            ).build()
    }
}