package com.github.kldmohammed.books.model

import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@javax.persistence.Table(name = "book")
@Entity
data class Book(
    @Id
    @Column(name = "id", nullable = false)
    var id: String = UUID.randomUUID().toString(),

    @NotEmpty(message = "Book name should not be empty")
    @Column(name = "book_name", nullable = false)
    var bookName: String? = null,


    @NotEmpty(message = "Author should not be empty or null")
    @Column(name = "author")
    var author: String? = null,

    @NotEmpty(message = "publisher should not be empty or null")
    @Column(name = "publisher")
    var publisher: String? = null,

    @NotNull(message = "isbn should not be empty or null")
    @Column(name = "isbn")
    var isbn: Long? = null,

    @NotNull(message = "description should not be empty or null")
    @Column(name = "description")
    var description: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Book

        return id != null && id == other.id
    }

    override fun hashCode(): Int = 967762358

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , bookName = $bookName , author = $author , publisher = $publisher , isbn = $isbn , description = $description )"
    }
}


