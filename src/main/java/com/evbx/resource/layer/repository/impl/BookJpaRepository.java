package com.evbx.resource.layer.repository.impl;

import com.evbx.resource.layer.repository.BookRepository;
import com.evbx.resource.model.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookJpaRepository extends JpaRepository<Book, Long>, BookRepository {

}
