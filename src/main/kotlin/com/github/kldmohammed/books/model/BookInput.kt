package com.github.kldmohammed.books.model

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class BookInput(


    @NotEmpty(message = "Book name should not be empty")
    @Size(min = 3, max = 255, message = "Book name should be between 3 and 255 characters")
    var bookName: String = "",


    @NotEmpty(message = "Author should not be empty or null")
    @Size(min = 3, max = 255, message = "Author should be between 3 and 255 characters")
    var author: String? = null,

    @NotEmpty(message = "Publisher should not be empty or null")
    @Size(min = 3, max = 255, message = "Publisher should be between 3 and 255 characters")
    var publisher: String? = null,

    @NotNull(message = "isbn should not be empty or null")
    @Size(min = 3, max = 255, message = "ISBN should be between 8 and 255 characters")
    var isbn: Long? = null,

    @NotNull(message = "Description should not be empty or null")
    @Size(min = 3, max = 255, message = "description should be between 3 and 255 characters")
    var description: String? = null,
)
