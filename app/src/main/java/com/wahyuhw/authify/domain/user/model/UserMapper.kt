package com.wahyuhw.authify.domain.user.model

import com.wahyuhw.authify.data.user.source.network.model.request.UserLoginRequest
import com.wahyuhw.authify.data.user.source.network.model.response.LoginResponse
import com.wahyuhw.authify.data.user.source.network.model.response.UserResponse
import com.wahyuhw.authify.utils.orZero

fun LoginResponse.toDomain(): Login = Login(token = token.orEmpty())

fun UserResponse.toDomain(): User =
	User(
		id = id.orZero(),
		email = email.orEmpty(),
		firstName = firstName.orEmpty(),
		lastName = lastName.orEmpty(),
		avatar = avatar.orEmpty()
	)

fun UserLoginParam.toRequest(): UserLoginRequest =
	UserLoginRequest(
		email = email,
		password = password
	)