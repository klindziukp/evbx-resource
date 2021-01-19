package com.evbx.resource.layer.service;

import com.evbx.resource.model.data.ItemData;
import com.evbx.resource.model.domain.Book;

public interface BookService extends BaseService {

  ItemData<Book> findAllBooks();

  Book findById(long id);

  Book save(Book book);

  Book update(long id, Book book);
}
