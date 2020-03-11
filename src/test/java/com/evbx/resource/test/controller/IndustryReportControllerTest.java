package com.evbx.resource.test.controller;

import com.evbx.resource.data.TestDataStorage;
import com.evbx.resource.layer.controller.BookController;
import com.evbx.resource.layer.controller.IndustryReportController;
import com.evbx.resource.model.domain.Book;
import com.evbx.resource.model.domain.IndustryReport;
import com.evbx.resource.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IndustryReportControllerTest extends BaseControllerTest {

    private MockMvc mockMvc;
    private IndustryReportController industryReportController;

    @Autowired
    public IndustryReportControllerTest(IndustryReportController industryReportController) {
        this.industryReportController = industryReportController;
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.industryReportController).build();
    }

    @Test
    void getReportTest() throws Exception {
        mockMvc.perform(get("/industry-reports/" + 100L)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllReportsTest() throws Exception {
        mockMvc.perform(get("/industry-reports")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", hasSize(3)))
                .andExpect(jsonPath("$.total", is(3)));
    }

    @Test
    void getAllReportsIdsTest() throws Exception {
        mockMvc.perform(get("/industry-reports/ids")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void addReportTest() throws Exception {
        IndustryReport industryReport = TestDataStorage.getMutationIndustryReport();
        mockMvc.perform(post("/industry-reports")
                .content(JsonUtil.toJson(industryReport))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.industryName", is(industryReport.getIndustryName())));
    }

    @Test
    void updateReportTest() throws Exception {
        IndustryReport industryReport = TestDataStorage.getRandomItem(TestDataStorage.getTestIndustryReports());
        String industryName = "updatedIndustryName";
        industryReport.setIndustryName(industryName);
        mockMvc.perform(patch("/industry-reports/" + 100L)
                .content(JsonUtil.toJson(industryReport))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.industryName", is(industryName)));
    }

    @Test
    void deleteBookTest() throws Exception {
        mockMvc.perform(delete("/industry-reports/" + 102L)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Deleted item with id = 102")));
    }
}