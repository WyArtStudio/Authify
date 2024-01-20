package com.wahyuhw.authify.base

import com.google.gson.annotations.SerializedName

data class ErrorResponse <T>(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("error")
    val error: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("token")
    val token: String?,
)