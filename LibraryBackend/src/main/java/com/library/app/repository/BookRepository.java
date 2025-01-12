package com.library.app.repository;

import com.library.app.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    // we have inherited all CRUD-Related methods

    Book findByTitle(String title);
    // this method will find book by title
    // we can add new database method here
    // the ones that are not present in the JpaRepository
}
