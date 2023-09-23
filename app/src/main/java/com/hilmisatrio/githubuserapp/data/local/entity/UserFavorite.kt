package com.hilmisatrio.githubuserapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserFavorite(
    @PrimaryKey(autoGenerate = false)
    var username: String = "",
    var urlImageAvatar: String? = null,
    var linkGithub: String = ""
)
