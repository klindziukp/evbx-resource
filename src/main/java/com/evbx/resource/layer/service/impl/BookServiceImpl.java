package com.evbx.resource.layer.service.impl;

import com.evbx.resource.constant.Item;
import com.evbx.resource.exception.ItemNotFoundException;
import com.evbx.resource.layer.repository.BookRepository;
import com.evbx.resource.layer.service.BookService;
import com.evbx.resource.model.domain.Book;
import com.evbx.resource.model.data.ItemData;
import com.evbx.resource.util.AuthUtil;
import com.evbx.resource.util.UpdateUtil;
import com.evbx.resource.util.ValidationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger LOGGER = LogManager.getLogger(BookServiceImpl.class);

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Cacheable(value= "allBooksCache", unless= "#result.getTotal() == 0")
    public ItemData<Book> findAllBooks() {
        LOGGER.info("Get all books");
        return new ItemData<>(bookRepository.findAll());
    }

    @Override
    @Cacheable(value= "bookCache", key= "'book'+#id")
    public Book findById(long id) {
        LOGGER.info("Get book with id = '{}'", id);
        return bookRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(Item.E_BOOK, id));
    }

    @Override
    @Caching(put = { @CachePut(value = "bookCache", key = "'book'+#book.id") },
             evict = { @CacheEvict(value = "allBooksCache", allEntries = true) })
    public Book save(Book book) {
        verifyBookPresence(book);
        book.setCreatedBy(AuthUtil.getUserName());
        Book savedBook = bookRepository.save(book);
        LOGGER.info("Book with id = '{}' saved successfully", savedBook.getId());
        return savedBook;
    }

    @Override
    @Caching(put = { @CachePut(value = "bookCache", key = "'book'+#book.id") },
             evict = { @CacheEvict(value = "allBooksCache", allEntries = true) })
    public Book update(long id, Book book) {
        Book persistedBook = bookRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(Item.E_BOOK, id));
        verifyBookPresence(book);
        UpdateUtil.updateItem(book, persistedBook);
        Book updatedBook = bookRepository.save(persistedBook);
        LOGGER.info("E-Book with id = '{}' saved successfully", updatedBook.getId());
        return updatedBook;
    }

    @Override
    public List<Long> getAllIds() {
        return bookRepository.getAllIds();
    }

    @Caching(evict = { @CacheEvict(value = "bookCache", key = "'book'+#id"),
                       @CacheEvict(value = "allBooksCache", allEntries = true) })
    public void deleteById(long id) {
        if (!bookRepository.existsById(id)) {
            throw new ItemNotFoundException(Item.E_BOOK, id);
        }
        bookRepository.softDelete(id, AuthUtil.getUserName());
        LOGGER.info("E-Book with id = '{}' deleted successfully", id);
    }

    private void verifyBookPresence(Book book) {
        String bookName = book.getBookName();
        if (Objects.nonNull(bookName)) {
            ValidationUtil.validateResourceName(bookName, bookRepository.getNames());
        }
    }
}
