package com.github.kldmohammed.books.model.errors

data class ApiError(private val codes: Array<String>, private val message: String)