package com.evbx.resource.test.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.evbx.resource.data.TestDataStorage;
import com.evbx.resource.layer.controller.BookController;
import com.evbx.resource.model.domain.Book;
import com.evbx.resource.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class BookControllerTest extends BaseControllerTest {

  private MockMvc mockMvc;
  private BookController bookController;

  @Autowired
  public BookControllerTest(BookController bookController) {
    this.bookController = bookController;
    this.mockMvc = MockMvcBuilders.standaloneSetup(this.bookController).build();
  }

  @Test
  void getBookTest() throws Exception {
    mockMvc
        .perform(get("/e-books/" + 100L).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void getAllBooksTest() throws Exception {
    mockMvc
        .perform(get("/e-books").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.items", hasSize(3)))
        .andExpect(jsonPath("$.total", is(3)));
  }

  @Test
  void getAllBooksIdsTest() throws Exception {
    mockMvc
        .perform(get("/e-books/ids").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  void addBookTest() throws Exception {
    Book mutationBook = TestDataStorage.getMutationBook();
    mockMvc
        .perform(
            post("/e-books")
                .content(JsonUtil.toJson(mutationBook))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.bookName", is(mutationBook.getBookName())));
  }

  @Test
  void updateBookTest() throws Exception {
    Book mutationBook = TestDataStorage.getRandomItem(TestDataStorage.getTestBooks());
    String bookName = "updatedBookName";
    mutationBook.setBookName("updatedBookName");
    mockMvc
        .perform(
            patch("/e-books/" + 100L)
                .content(JsonUtil.toJson(mutationBook))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.bookName", is(bookName)));
  }

  @Test
  void deleteBookTest() throws Exception {
    mockMvc
        .perform(delete("/e-books/" + 102L).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", is("Deleted item with id = 102")));
  }
}
