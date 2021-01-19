package com.evbx.resource.layer.repository;

import com.evbx.resource.layer.repository.projection.NameProjection;
import com.evbx.resource.model.domain.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends BaseRepository {

  List<Book> findAll();

  Optional<Book> findById(long id);

  Book save(Book book);

  @Override
  @Query(value = "SELECT book_name AS name FROM e_books", nativeQuery = true)
  List<NameProjection> getNames();

  @Override
  @Query(value = "SELECT id FROM e_books", nativeQuery = true)
  List<Long> getAllIds();
}
