package com.github.kldmohammed.books.model.errors

data class BookNotFoundException(override val message: String) : RuntimeException(message) {
}