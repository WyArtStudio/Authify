package com.wahyuhw.authify.domain.util

import com.wahyuhw.authify.data.user.source.network.model.response.UserResponse
import com.wahyuhw.authify.data.util.BasePaginationResponse
import com.wahyuhw.authify.domain.user.model.User
import com.wahyuhw.authify.domain.user.model.toDomain
import com.wahyuhw.authify.utils.orZero

fun BasePaginationResponse<UserResponse>.toDomain(): BasePagination<User> =
	BasePagination(
		page = page.orZero(),
		data = data?.map { it.toDomain() }.orEmpty(),
		perPage = perPage.orZero(),
		total = total.orZero(),
		totalPages = totalPages.orZero()
	)