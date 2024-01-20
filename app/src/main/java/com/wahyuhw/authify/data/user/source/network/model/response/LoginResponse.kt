package com.wahyuhw.authify.data.user.source.network.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token")
    val token: String? = null
)