package com.evbx.resource.test.service;

import com.evbx.resource.data.TestDataStorage;
import com.evbx.resource.exception.ItemNotFoundException;
import com.evbx.resource.layer.service.SpecService;
import com.evbx.resource.model.domain.Specification;
import com.evbx.resource.support.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

import static com.evbx.resource.support.Step.__GIVEN;
import static com.evbx.resource.support.Step.__THEN;
import static com.evbx.resource.support.Step.__WHEN;
import static org.assertj.core.api.Assertions.assertThat;

class SpecServiceTest extends BaseServiceTest {

    private SpecService specService;

    @Autowired
    public SpecServiceTest(SpecService specService) {
        this.specService = specService;
    }

    @Test
    void findAllSpecificationsTest() {
        __GIVEN();
        List<Specification> expectedSpecifications = TestDataStorage.getTestSpecs();
        __WHEN();
        List<Specification> actualSpecifications = specService.findAllSpecifications().getItems();
        __THEN();
        assertThat(actualSpecifications).usingElementComparatorIgnoringFields(Ignore.getUpdatableEntityFields())
                .isEqualTo(expectedSpecifications);
    }

    @Test
    void findSpecificationByIdTest() {
        __GIVEN();
        Specification expectedSpecification = TestDataStorage.getRandomItem(TestDataStorage.getTestSpecs());
        __WHEN();
        Long id = expectedSpecification.getId();
        Specification actualSpecification = specService.findById(id);
        __THEN();
        assertThat(actualSpecification).isEqualToIgnoringGivenFields(expectedSpecification,
                Ignore.getUpdatableEntityFields());
    }

    @Test
    void saveSpecificationTest() {
        __GIVEN();
        mockSecurityContext();
        Specification mutationSpecification = TestDataStorage.getMutationSpec();
        __WHEN();
        Specification savedSpecification = specService.save(mutationSpecification);
        Long id = savedSpecification.getId();
        Specification extractedSpecification = specService.findById(id);
        __THEN();
        assertThat(mutationSpecification).isEqualToIgnoringGivenFields(savedSpecification,
                Ignore.getUpdatableEntityFields());
        assertThat(mutationSpecification).isEqualToIgnoringGivenFields(extractedSpecification,
                Ignore.getUpdatableEntityFields());
    }

    @Test
    void updateSpecificationTest() {
        __GIVEN();
        mockSecurityContext();
        Specification mutationSpecification = TestDataStorage.getMutationSpec();
        Specification expectedSpecification = TestDataStorage.getRandomItem(TestDataStorage.getTestSpecs());
        expectedSpecification.setSpecificationName(mutationSpecification.getSpecificationName());
        expectedSpecification.setText(mutationSpecification.getText());
        long totalBefore = specService.findAllSpecifications().getTotal();
        __WHEN();
        Specification savedSpecification = specService.save(expectedSpecification);
        Long id = savedSpecification.getId();
        long totalAfter = specService.findAllSpecifications().getTotal();
        Specification extractedSpecification = specService.findById(id);
        __THEN();
        assertThat(extractedSpecification).isEqualToIgnoringGivenFields(expectedSpecification,
                Ignore.getUpdatableEntityFields());
        assertThat(totalAfter).isEqualTo(totalBefore);
    }

    @Test
    void getAllIdsSpecificationTest() {
        __GIVEN();
        List<Long> expectedIds = TestDataStorage.getIdsFromList(TestDataStorage.getTestSpecs());
        __WHEN();
        List<Long> actualIds = specService.getAllIds();
        __THEN();
        assertThat(actualIds).isEqualTo(expectedIds);
    }

    @Test
    void deleteSpecificationByIdTest() {
        __GIVEN();
        List<Specification> SpecificationsBeforeDelete = specService.findAllSpecifications().getItems();
        __WHEN();
        Specification SpecificationToDelete = TestDataStorage.getRandomItem(SpecificationsBeforeDelete);
        specService.deleteById(SpecificationToDelete.getId());
        __THEN();
        List<Specification> SpecificationsAfterDelete = specService.findAllSpecifications().getItems();
        assertThat(SpecificationsAfterDelete).doesNotContain(SpecificationToDelete);
    }

    @Test
    void itemNotFoundExceptionTest() {
        __GIVEN();
        List<Long> ids = specService.getAllIds();
        __WHEN();
        long nonPresentId = Collections.max(ids) + 1L;
        __THEN();
        Assertions.assertThrows(ItemNotFoundException.class, () -> specService.findById(nonPresentId));
    }
}