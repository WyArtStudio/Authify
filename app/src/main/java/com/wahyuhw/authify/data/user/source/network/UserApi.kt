package com.wahyuhw.authify.data.user.source.network

import com.wahyuhw.authify.base.BaseModel
import com.wahyuhw.authify.data.util.BasePaginationResponse
import com.wahyuhw.authify.data.util.BaseResponse
import com.wahyuhw.authify.data.user.source.network.model.request.UserLoginRequest
import com.wahyuhw.authify.data.user.source.network.model.response.LoginResponse
import com.wahyuhw.authify.data.user.source.network.model.response.UserResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class UserApi (private val apiClient: UserApiClient): UserApiClient {
	
	override suspend fun login(request: UserLoginRequest): Response<LoginResponse> {
		return apiClient.login(request)
	}
	
	override suspend fun logout(): Response<BaseModel> {
		return apiClient.logout()
	}
	
	override suspend fun getUserById(userId: String): Response<BaseResponse<UserResponse>> {
		return apiClient.getUserById(userId)
	}
	
	override suspend fun getListUser(
		page: Int,
		pageSize: Int
	): Response<BasePaginationResponse<UserResponse>> {
		return apiClient.getListUser(page, pageSize)
	}
}