package com.github.kldmohammed.books.repositories;

import com.github.kldmohammed.books.model.Book
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, String> {
}