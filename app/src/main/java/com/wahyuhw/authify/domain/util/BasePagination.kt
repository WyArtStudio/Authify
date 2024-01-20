package com.wahyuhw.authify.domain.util

data class BasePagination<T>(
    val page: Int = 0,
    val data: List<T> = emptyList(),
    val perPage: Int = 0,
    val total: Int = 0,
    val totalPages: Int = 0
)