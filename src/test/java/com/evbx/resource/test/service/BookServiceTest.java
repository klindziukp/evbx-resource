package com.evbx.resource.test.service;

import static com.evbx.resource.support.Step.__GIVEN;
import static com.evbx.resource.support.Step.__THEN;
import static com.evbx.resource.support.Step.__WHEN;
import static org.assertj.core.api.Assertions.assertThat;

import com.evbx.resource.data.TestDataStorage;
import com.evbx.resource.exception.ItemNotFoundException;
import com.evbx.resource.layer.service.BookService;
import com.evbx.resource.model.domain.Book;
import com.evbx.resource.support.Ignore;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BookServiceTest extends BaseServiceTest {

  private BookService bookService;

  @Autowired
  public BookServiceTest(BookService bookService) {
    this.bookService = bookService;
  }

  @Test
  void findAllBooksTest() {
    __GIVEN();
    List<Book> expectedBooks = TestDataStorage.getTestBooks();
    __WHEN();
    List<Book> actualBooks = bookService.findAllBooks().getItems();
    __THEN();
    assertThat(actualBooks)
        .usingElementComparatorIgnoringFields(Ignore.getUpdatableEntityFields())
        .isEqualTo(expectedBooks);
  }

  @Test
  void findBookByIdTest() {
    __GIVEN();
    Book expectedBook = TestDataStorage.getRandomItem(TestDataStorage.getTestBooks());
    __WHEN();
    Long id = expectedBook.getId();
    Book actualBook = bookService.findById(id);
    __THEN();
    assertThat(actualBook)
        .isEqualToIgnoringGivenFields(expectedBook, Ignore.getUpdatableEntityFields());
  }

  @Test
  void saveBookTest() {
    __GIVEN();
    Book mutationBook = TestDataStorage.getMutationBook();
    __WHEN();
    Book savedBook = bookService.save(mutationBook);
    Long id = savedBook.getId();
    Book extractedBook = bookService.findById(id);
    __THEN();
    assertThat(mutationBook)
        .isEqualToIgnoringGivenFields(savedBook, Ignore.getUpdatableEntityFields());
    assertThat(mutationBook)
        .isEqualToIgnoringGivenFields(extractedBook, Ignore.getUpdatableEntityFields());
  }

  @Test
  void updateBookTest() {
    __GIVEN();
    Book mutationBook = TestDataStorage.getMutationBook();
    Book expectedBook = TestDataStorage.getRandomItem(TestDataStorage.getTestBooks());
    expectedBook.setBookName(mutationBook.getBookName());
    expectedBook.setText(mutationBook.getText());
    long totalBefore = bookService.findAllBooks().getTotal();
    __WHEN();
    Book savedBook = bookService.save(expectedBook);
    Long id = savedBook.getId();
    long totalAfter = bookService.findAllBooks().getTotal();
    Book extractedBook = bookService.findById(id);
    __THEN();
    assertThat(extractedBook)
        .isEqualToIgnoringGivenFields(expectedBook, Ignore.getUpdatableEntityFields());
    assertThat(totalAfter).isEqualTo(totalBefore);
  }

  @Test
  void getAllIdsBookTest() {
    __GIVEN();
    List<Long> expectedIds = TestDataStorage.getIdsFromList(TestDataStorage.getTestBooks());
    __WHEN();
    List<Long> actualIds = bookService.getAllIds();
    __THEN();
    assertThat(actualIds).isEqualTo(expectedIds);
  }

  @Test
  void deleteBookByIdTest() {
    __GIVEN();
    List<Book> booksBeforeDelete = bookService.findAllBooks().getItems();
    __WHEN();
    Book bookToDelete = TestDataStorage.getRandomItem(booksBeforeDelete);
    bookService.deleteById(bookToDelete.getId());
    __THEN();
    List<Book> booksAfterDelete = bookService.findAllBooks().getItems();
    assertThat(booksAfterDelete).doesNotContain(bookToDelete);
  }

  @Test
  void itemNotFoundExceptionGetTest() {
    __GIVEN();
    List<Long> ids = bookService.getAllIds();
    __WHEN();
    long nonPresentId = Collections.max(ids) + 100L;
    __THEN();
    Assertions.assertThrows(ItemNotFoundException.class, () -> bookService.findById(nonPresentId));
  }

  @Test
  void itemNotFoundExceptionUpdateTest() {
    __GIVEN();
    List<Long> ids = bookService.getAllIds();
    __WHEN();
    long nonPresentId = Collections.max(ids) + 101L;
    __THEN();
    Assertions.assertThrows(
        ItemNotFoundException.class, () -> bookService.update(nonPresentId, new Book()));
  }

  @Test
  void itemNotFoundExceptionDeleteTest() {
    __GIVEN();
    List<Long> ids = bookService.getAllIds();
    __WHEN();
    long nonPresentId = Collections.max(ids) + 102L;
    __THEN();
    Assertions.assertThrows(
        ItemNotFoundException.class, () -> bookService.deleteById(nonPresentId));
  }
}
