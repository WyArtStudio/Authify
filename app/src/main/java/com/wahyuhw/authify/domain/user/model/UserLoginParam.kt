package com.wahyuhw.authify.domain.user.model

import com.wahyuhw.authify.utils.emptyString

data class UserLoginParam(
	val email: String = emptyString(),
	val password: String = emptyString(),
)