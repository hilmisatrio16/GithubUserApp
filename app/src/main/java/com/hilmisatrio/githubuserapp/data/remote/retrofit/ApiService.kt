package com.hilmisatrio.githubuserapp.data.remote.retrofit

import com.hilmisatrio.githubuserapp.data.remote.response.ResponseDetailUser
import com.hilmisatrio.githubuserapp.data.remote.response.ResponseFollow
import com.hilmisatrio.githubuserapp.data.remote.response.ResponseSearchUsers
import com.hilmisatrio.githubuserapp.data.remote.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username : String
    ) : ResponseDetailUser

    @GET("users/{username}/followers")
    suspend fun getDataFollowers(
        @Path("username") username : String
    ) : ResponseFollow

    @GET("users/{username}/following")
    suspend fun getDataFollowing(
        @Path("username") username : String
    ) : ResponseFollow

    @GET("search/users")
    suspend fun getUsers(
        @Query("q") user : String
    ) : ResponseSearchUsers
}