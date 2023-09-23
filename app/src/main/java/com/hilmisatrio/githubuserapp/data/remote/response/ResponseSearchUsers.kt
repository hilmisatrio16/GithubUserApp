package com.hilmisatrio.githubuserapp.data.remote.response


import com.google.gson.annotations.SerializedName

data class ResponseSearchUsers(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<ItemUser>,
    @SerializedName("total_count")
    val totalCount: Int
)