package com.wahyuhw.authify.data.user.source.network

import com.wahyuhw.authify.base.BaseModel
import com.wahyuhw.authify.data.util.BasePaginationResponse
import com.wahyuhw.authify.data.util.BaseResponse
import com.wahyuhw.authify.data.user.source.network.model.request.UserLoginRequest
import com.wahyuhw.authify.data.user.source.network.model.response.LoginResponse
import com.wahyuhw.authify.data.user.source.network.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiClient {
	
	@POST("login")
	suspend fun login(
		@Body request: UserLoginRequest
	): Response<LoginResponse>
	
	@POST("logout")
	suspend fun logout(): Response<BaseModel>
	
	@GET("users/{userId}")
	suspend fun getUserById(
		@Path("userId") userId: String
	): Response<BaseResponse<UserResponse>>
	
	@GET("users")
	suspend fun getListUser(
		@Query("page") page: Int,
		@Query("per_page") pageSize: Int = 10,
	): Response<BasePaginationResponse<UserResponse>>
}