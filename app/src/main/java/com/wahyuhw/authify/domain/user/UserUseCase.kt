package com.wahyuhw.authify.domain.user

import com.wahyuhw.authify.data.user.source.network.model.request.UserLoginRequest
import com.wahyuhw.authify.domain.user.model.Login
import com.wahyuhw.authify.domain.user.model.User
import com.wahyuhw.authify.domain.user.model.UserLoginParam
import com.wahyuhw.authify.domain.util.BasePagination
import com.wahyuhw.authify.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
	suspend fun login(
		param: UserLoginParam
	): Flow<Resource<Login>>
	
	suspend fun logout(): Flow<Resource<Boolean>>
	
	suspend fun getUserById(
		userId: String
	): Flow<Resource<User>>
	
	suspend fun getListUser(
		page: Int,
		pageSize: Int = 10,
	): Flow<Resource<BasePagination<User>>>
}