package com.wahyuhw.authify.domain.user

import com.wahyuhw.authify.data.user.UserRepository
import com.wahyuhw.authify.domain.user.model.Login
import com.wahyuhw.authify.domain.user.model.User
import com.wahyuhw.authify.domain.user.model.UserLoginParam
import com.wahyuhw.authify.domain.user.model.toRequest
import com.wahyuhw.authify.domain.util.BasePagination
import com.wahyuhw.authify.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

class UserInteractor(private val repository: UserRepository): UserUseCase {
	override suspend fun login(param: UserLoginParam): Flow<Resource<Login>> {
		return repository.login(param.toRequest())
	}
	
	override suspend fun logout(): Flow<Resource<Boolean>> {
		return repository.logout()
	}
	
	override suspend fun getUserById(userId: String): Flow<Resource<User>> {
		return repository.getUserById(userId)
	}
	
	override suspend fun getListUser(
		page: Int,
		pageSize: Int
	): Flow<Resource<BasePagination<User>>> {
		return repository.getListUser(page, pageSize)
	}
}