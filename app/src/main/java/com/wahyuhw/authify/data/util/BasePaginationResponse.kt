package com.wahyuhw.authify.data.util

import com.google.gson.annotations.SerializedName

data class BasePaginationResponse <T>(
    @SerializedName("page") val page: Int? = null,
    @SerializedName("data") val data: List<T>? = null,
    @SerializedName("per_page") val perPage: Int? = null,
    @SerializedName("total") val total: Int? = null,
    @SerializedName("total_pages") val totalPages: Int? = null
)