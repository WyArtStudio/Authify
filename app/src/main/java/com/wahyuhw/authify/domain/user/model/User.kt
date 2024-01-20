package com.wahyuhw.authify.domain.user.model

import com.wahyuhw.authify.utils.emptyString

data class User(
	val id: Int = 0,
	val firstName: String = emptyString(),
	val lastName: String = emptyString(),
	val email: String = emptyString(),
	val avatar: String = emptyString(),
)