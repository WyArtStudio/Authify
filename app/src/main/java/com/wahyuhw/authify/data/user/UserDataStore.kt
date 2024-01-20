package com.wahyuhw.authify.data.user

import com.wahyuhw.authify.data.user.source.network.UserApiClient
import com.wahyuhw.authify.data.user.source.network.model.request.UserLoginRequest
import com.wahyuhw.authify.data.util.call
import com.wahyuhw.authify.data.util.callWithBase
import com.wahyuhw.authify.data.util.mapToDomain
import com.wahyuhw.authify.domain.user.model.Login
import com.wahyuhw.authify.domain.user.model.User
import com.wahyuhw.authify.domain.user.model.toDomain
import com.wahyuhw.authify.domain.util.BasePagination
import com.wahyuhw.authify.domain.util.Resource
import com.wahyuhw.authify.domain.util.toDomain
import com.wahyuhw.authify.utils.isNotNull
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

class UserDataStore (private val webService: UserApiClient) : UserRepository {
	override suspend fun login(request: UserLoginRequest): Flow<Resource<Login>> {
		return webService.login(request).call().mapToDomain { it.toDomain() }
	}
	
	override suspend fun logout(): Flow<Resource<Boolean>> {
		return webService.logout().call().mapToDomain { it.isNotNull() }
	}
	
	override suspend fun getUserById(userId: String): Flow<Resource<User>> {
		return webService.getUserById(userId).callWithBase().mapToDomain { it.toDomain() }
	}
	
	override suspend fun getListUser(
		page: Int,
		pageSize: Int
	): Flow<Resource<BasePagination<User>>> {
		return webService.getListUser(page, pageSize).call().mapToDomain { it.toDomain() }
	}
}