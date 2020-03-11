package com.evbx.resource.layer.service;

import com.evbx.resource.model.domain.Book;
import com.evbx.resource.model.data.ItemData;

public interface BookService extends BaseService {

    ItemData<Book> findAllBooks();
    Book findById(long id);
    Book save(Book book);
    Book update(long id, Book book);
}
