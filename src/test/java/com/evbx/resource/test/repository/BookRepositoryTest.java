package com.evbx.resource.test.repository;

import static com.evbx.resource.support.Step.__GIVEN;
import static com.evbx.resource.support.Step.__THEN;
import static com.evbx.resource.support.Step.__WHEN;
import static org.assertj.core.api.Assertions.assertThat;

import com.evbx.resource.constant.Item;
import com.evbx.resource.data.TestDataProjectionStorage;
import com.evbx.resource.data.TestDataStorage;
import com.evbx.resource.exception.ItemNotFoundException;
import com.evbx.resource.layer.repository.BookRepository;
import com.evbx.resource.layer.repository.projection.NameProjection;
import com.evbx.resource.model.domain.Book;
import com.evbx.resource.support.Ignore;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BookRepositoryTest extends BaseRepositoryTest {

  private BookRepository bookRepository;

  @Autowired
  public BookRepositoryTest(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Test
  void findAllBooksTest() {
    __GIVEN();
    List<Book> expectedBooks = TestDataStorage.getTestBooks();
    __WHEN();
    List<Book> actualBooks = bookRepository.findAll();
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
    Book actualBook =
        bookRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(Item.E_BOOK, id));
    __THEN();
    assertThat(actualBook)
        .isEqualToIgnoringGivenFields(expectedBook, Ignore.getUpdatableEntityFields());
  }

  @Test
  void saveBookTest() {
    __GIVEN();
    Book mutationBook = TestDataStorage.getMutationBook();
    __WHEN();
    Book savedBook = bookRepository.save(mutationBook);
    Long id = savedBook.getId();
    Book extractedBook =
        bookRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(Item.E_BOOK, id));
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
    int itemsSizeBefore = bookRepository.findAll().size();
    __WHEN();
    Book savedBook = bookRepository.save(expectedBook);
    Long id = savedBook.getId();
    int itemsSizeAfter = bookRepository.findAll().size();
    Book extractedBook =
        bookRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(Item.E_BOOK, id));
    __THEN();
    assertThat(extractedBook)
        .isEqualToIgnoringGivenFields(expectedBook, Ignore.getUpdatableEntityFields());
    assertThat(itemsSizeAfter).isEqualTo(itemsSizeBefore);
  }

  @Test
  void deleteByIdBookTest() {
    __GIVEN();
    List<Book> booksBeforeDelete = bookRepository.findAll();
    __WHEN();
    Book bookToDelete = TestDataStorage.getRandomItem(booksBeforeDelete);
    bookRepository.softDelete(bookToDelete.getId(), "script");
    __THEN();
    List<Book> booksAfterDelete = bookRepository.findAll();
    assertThat(booksAfterDelete).doesNotContain(bookToDelete);
  }

  @Test
  void getBookNamesProjectionTest() {
    __GIVEN();
    List<NameProjection> expectedBookNameProjections =
        TestDataProjectionStorage.getBookNameProjections();
    __WHEN();
    List<NameProjection> actualBookNameProjections = bookRepository.getNames();
    __THEN();
    projectionVerificationService.verifyNameProjections(
        actualBookNameProjections, expectedBookNameProjections);
  }

  @Test
  void getBookIdsTest() {
    __GIVEN();
    List<Long> expectedIds = TestDataStorage.getIdsFromList(TestDataStorage.getTestBooks());
    __WHEN();
    List<Long> actualIds = bookRepository.getAllIds();
    __THEN();
    assertThat(actualIds).isEqualTo(expectedIds);
  }

  @Test
  void existsByIdTest() {
    __GIVEN();
    Long id = TestDataStorage.getRandomItem(TestDataStorage.getTestBooks()).getId();
    __WHEN();
    boolean isExists = bookRepository.existsById(id);
    __THEN();
    assertThat(isExists).isTrue();
  }
}
