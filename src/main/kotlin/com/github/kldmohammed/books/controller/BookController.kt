package com.github.kldmohammed.books.controller

import com.github.kldmohammed.books.model.Book
import com.github.kldmohammed.books.model.BookInput
import com.github.kldmohammed.books.model.errors.ApiError
import com.github.kldmohammed.books.model.errors.BookNotFoundException
import com.github.kldmohammed.books.repositories.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.ObjectError
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.function.Supplier
import java.util.stream.Collectors
import javax.transaction.Transactional
import javax.validation.ConstraintViolationException
import javax.validation.Valid
import javax.validation.constraints.Positive


@Validated
@RestController
@RequestMapping("/api/books")
class BookController {

    @Autowired
    private lateinit var repository: BookRepository

    @PostMapping
    @Transactional
    fun save(@RequestBody book: @Valid BookInput): Book? {
        return repository.saveAndFlush(
            Book(
                bookName = book.bookName,
                publisher = book.publisher,
                author = book.author,
                isbn = book.isbn,
                description = book.description
            )
        )
    }

    @GetMapping
    fun findAll(
       /* @RequestParam(
            required = false,
            defaultValue = "1"
        ) page: @Valid @Positive(message = "Page number should be a positive number") Int,
        @RequestParam(
            required = false,
            defaultValue = "10"
        ) size: @Valid @Positive(message = "Page size should be a positive number") Int
    */): ResponseEntity<List<Book>> {
        val headers = HttpHeaders()
        val books: List<Book> = repository.findAll()
        println("bbbbbb $books")
//        headers.add("X-Users-Total", users.totalElements.toString())
        return ResponseEntity<List<Book>>(books, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: String): Book? {
        return repository
            .findById(id)
            .orElseThrow { BookNotFoundException("Book with id '$id' is not found") }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): List<ApiError?>? {
        return ex.bindingResult
            .allErrors.stream()
            .map { err: ObjectError ->
                ApiError(
                    err.codes!!,
                    err.defaultMessage!!
                )
            }
            .collect(Collectors.toList())
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleValidationExceptions(ex: ConstraintViolationException): List<ApiError?>? {
        return ex.constraintViolations
            .stream()
            .map { err -> ApiError(arrayOf(err.propertyPath.toString()), err.message) }
            .collect(Collectors.toList())
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFoundException::class)
    fun handleNotFoundExceptions(ex: BookNotFoundException): List<ApiError?>? {
        return Collections.singletonList(ApiError(arrayOf("book.notfound"), ex.message))
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleOtherException(ex: Exception): List<ApiError?>? {
        return Collections.singletonList(ApiError(arrayOf(ex.javaClass.canonicalName), ex.message!!))
    }
}