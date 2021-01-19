package com.evbx.resource.layer.controller;

import com.evbx.resource.layer.service.BookService;
import com.evbx.resource.model.data.ItemData;
import com.evbx.resource.model.domain.Book;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

  private BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping(value = "/e-books/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Book> getBook(@PathVariable Long id) {
    return ResponseEntity.ok(bookService.findById(id));
  }

  @GetMapping(value = "/e-books", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ItemData<Book>> getAllBooks() {
    return ResponseEntity.ok(bookService.findAllBooks());
  }

  @GetMapping(value = "/e-books/ids", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Long>> getAllBooksIds() {
    return ResponseEntity.ok(bookService.getAllIds());
  }

  @PostMapping(value = "/e-books", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity addBook(@Valid @RequestBody Book book) {
    return ResponseEntity.ok(bookService.save(book));
  }

  @PatchMapping(value = "/e-books/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Book> updateBook(@PathVariable long id, @RequestBody Book book) {
    return ResponseEntity.ok(bookService.update(id, book));
  }

  @DeleteMapping(value = "/e-books/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity deleteBook(@PathVariable long id) {
    bookService.deleteById(id);
    return ResponseEntity.ok("Deleted item with id = " + id);
  }
}
