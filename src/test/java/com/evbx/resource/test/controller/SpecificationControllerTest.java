package com.evbx.resource.test.controller;

import com.evbx.resource.data.TestDataStorage;
import com.evbx.resource.layer.controller.SpecificationController;
import com.evbx.resource.model.domain.Specification;
import com.evbx.resource.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SpecificationControllerTest extends BaseControllerTest {

    private MockMvc mockMvc;
    private SpecificationController specificationController;

    @Autowired
    public SpecificationControllerTest(SpecificationController specificationController) {
        this.specificationController = specificationController;
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.specificationController).build();
    }

    @Test
    void getSpecificationTest() throws Exception {
        mockMvc.perform(get("/specifications/" + 100L)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllSpecificationsTest() throws Exception {
        mockMvc.perform(get("/specifications")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", hasSize(3)))
                .andExpect(jsonPath("$.total", is(3)));
    }

    @Test
    void getAllSpecificationsIdsTest() throws Exception {
        mockMvc.perform(get("/specifications/ids")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void addSpecificationTest() throws Exception {
        Specification specification = TestDataStorage.getMutationSpec();
        mockMvc.perform(post("/specifications")
                .content(JsonUtil.toJson(specification))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specificationName", is(specification.getSpecificationName())));
    }

    @Test
    void updateSpecificationTest() throws Exception {
        Specification specification = TestDataStorage.getRandomItem(TestDataStorage.getTestSpecs());
        String specificationName = "updatedSpecName";
        specification.setSpecificationName(specificationName);
        mockMvc.perform(patch("/specifications/" + 100L)
                .content(JsonUtil.toJson(specification))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specificationName", is(specificationName)));
    }

    @Test
    void deleteBookTest() throws Exception {
        mockMvc.perform(delete("/specifications/" + 102L)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Deleted item with id = 102")));
    }
}