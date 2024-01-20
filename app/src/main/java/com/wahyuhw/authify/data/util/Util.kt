package com.wahyuhw.authify.data.util

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.wahyuhw.authify.base.ErrorResponse
import com.wahyuhw.authify.domain.util.Resource
import com.wahyuhw.authify.utils.emptyString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.Response

fun <T, U> Flow<ApiResult<T>>.mapToDomain(mapper: (T) -> U): Flow<Resource<U>> =
    this.map {
        when (it) {
            is ApiResult.Success -> {
                Resource.Success(it.result?.let { mappedData -> mapper.invoke(mappedData) })
            }
            else -> {
                Resource.Error(it.errorCode ?: 999, it.errorMessage ?: emptyString())
            }
        }
    }

fun <T> Response<T>.call(): Flow<ApiResult<T>> =
    flow<ApiResult<T>> {
        try {
            val response = this@call
            if (response.isSuccessful) {
                emit(ApiResult.Success(response.body()))
            } else {
                response.errorBody()?.let {
                    val errorResponse = Gson().fromJson(it.string(), ErrorResponse::class.java)
                    val message = errorResponse.error.orEmpty()
                    emit(ApiResult.Error(errorResponse.status, message))
                }
            }
        } catch (e: JsonSyntaxException) {
            emit(ApiResult.Error(502, "Error internal server"))
        }
    }.flowOn(Dispatchers.IO)

fun <T> Response<BaseResponse<T>>.callWithBase(): Flow<ApiResult<T>> =
    flow<ApiResult<T>> {
        try {
            val response = this@callWithBase
            if (response.isSuccessful) {
                emit(ApiResult.Success(response.body()?.data))
            } else {
                response.errorBody()?.let {
                    val errorResponse = Gson().fromJson(it.string(), ErrorResponse::class.java)
                    val message = errorResponse.error.orEmpty()
                    emit(ApiResult.Error(errorResponse.status, message))
                }
            }
        } catch (e: JsonSyntaxException) {
            emit(ApiResult.Error(502, "Error internal server"))
        }
    }.flowOn(Dispatchers.IO)