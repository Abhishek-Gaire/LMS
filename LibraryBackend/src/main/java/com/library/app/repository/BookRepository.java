package com.library.app.repository;

import com.library.app.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    // we have inherited all CRUD-Related methods

    List<Book> findByTitleContainingIgnoreCase(String title);

    //find Book by title and author
    List<Book> findByTitleAndAuthorContainingIgnoreCase(String title, String author);

    //perform search by using CriteriaBuilder and CriteriaQuery
}
