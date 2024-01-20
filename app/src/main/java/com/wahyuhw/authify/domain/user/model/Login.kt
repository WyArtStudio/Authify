package com.wahyuhw.authify.domain.user.model

import com.wahyuhw.authify.utils.emptyString

data class Login(
    val token: String = emptyString()
)