package com.evbx.resource.test.repository;

import static com.evbx.resource.support.Step.__GIVEN;
import static com.evbx.resource.support.Step.__THEN;
import static com.evbx.resource.support.Step.__WHEN;
import static org.assertj.core.api.Assertions.assertThat;

import com.evbx.resource.constant.Item;
import com.evbx.resource.data.TestDataProjectionStorage;
import com.evbx.resource.data.TestDataStorage;
import com.evbx.resource.exception.ItemNotFoundException;
import com.evbx.resource.layer.repository.SpecRepository;
import com.evbx.resource.layer.repository.projection.NameProjection;
import com.evbx.resource.model.domain.Specification;
import com.evbx.resource.support.Ignore;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SpecRepositoryTest extends BaseRepositoryTest {

  private SpecRepository specRepository;

  @Autowired
  public SpecRepositoryTest(SpecRepository specRepository) {
    this.specRepository = specRepository;
  }

  @Test
  void findAllSpecificationsTest() {
    __GIVEN();
    List<Specification> expectedSpecifications = TestDataStorage.getTestSpecs();
    __WHEN();
    List<Specification> actualSpecifications = specRepository.findAll();
    __THEN();
    assertThat(actualSpecifications)
        .usingElementComparatorIgnoringFields(Ignore.getUpdatableEntityFields())
        .isEqualTo(expectedSpecifications);
  }

  @Test
  void findSpecificationByIdTest() {
    __GIVEN();
    Specification expectedSpecification =
        TestDataStorage.getRandomItem(TestDataStorage.getTestSpecs());
    __WHEN();
    Long id = expectedSpecification.getId();
    Specification actualSpecification =
        specRepository
            .findById(id)
            .orElseThrow(() -> new ItemNotFoundException(Item.SPECIFICATION, id));
    __THEN();
    assertThat(actualSpecification)
        .isEqualToIgnoringGivenFields(expectedSpecification, Ignore.getUpdatableEntityFields());
  }

  @Test
  void saveSpecificationTest() {
    __GIVEN();
    Specification mutationSpecification = TestDataStorage.getMutationSpec();
    __WHEN();
    Specification savedSpecification = specRepository.save(mutationSpecification);
    Long id = savedSpecification.getId();
    Specification extractedSpecification =
        specRepository
            .findById(id)
            .orElseThrow(() -> new ItemNotFoundException(Item.SPECIFICATION, id));
    __THEN();
    assertThat(mutationSpecification)
        .isEqualToIgnoringGivenFields(savedSpecification, Ignore.getUpdatableEntityFields());
    assertThat(mutationSpecification)
        .isEqualToIgnoringGivenFields(extractedSpecification, Ignore.getUpdatableEntityFields());
  }

  @Test
  void updateSpecificationTest() {
    __GIVEN();
    Specification mutationSpecification = TestDataStorage.getMutationSpec();
    Specification expectedSpecification =
        TestDataStorage.getRandomItem(TestDataStorage.getTestSpecs());
    expectedSpecification.setSpecificationName(mutationSpecification.getSpecificationName());
    expectedSpecification.setText(mutationSpecification.getText());
    int itemsSizeBefore = specRepository.findAll().size();
    __WHEN();
    Specification savedSpecification = specRepository.save(expectedSpecification);
    Long id = savedSpecification.getId();
    int itemsSizeAfter = specRepository.findAll().size();
    Specification extractedSpecification =
        specRepository
            .findById(id)
            .orElseThrow(() -> new ItemNotFoundException(Item.SPECIFICATION, id));
    __THEN();
    assertThat(extractedSpecification)
        .isEqualToIgnoringGivenFields(expectedSpecification, Ignore.getUpdatableEntityFields());
    assertThat(itemsSizeAfter).isEqualTo(itemsSizeBefore);
  }

  @Test
  void deleteByIdSpecificationTest() {
    __GIVEN();
    List<Specification> SpecificationsBeforeDelete = specRepository.findAll();
    __WHEN();
    Specification SpecificationToDelete = TestDataStorage.getRandomItem(SpecificationsBeforeDelete);
    specRepository.softDelete(SpecificationToDelete.getId(), "script");
    __THEN();
    List<Specification> SpecificationsAfterDelete = specRepository.findAll();
    assertThat(SpecificationsAfterDelete).doesNotContain(SpecificationToDelete);
  }

  @Test
  void getSpecificationNamesProjectionTest() {
    __GIVEN();
    List<NameProjection> expectedSpecificationNameProjections =
        TestDataProjectionStorage.getSpecNameProjections();
    __WHEN();
    List<NameProjection> actualSpecificationNameProjections = specRepository.getNames();
    __THEN();
    projectionVerificationService.verifyNameProjections(
        actualSpecificationNameProjections, expectedSpecificationNameProjections);
  }

  @Test
  void getSpecificationIdsTest() {
    __GIVEN();
    List<Long> expectedIds = TestDataStorage.getIdsFromList(TestDataStorage.getTestSpecs());
    __WHEN();
    List<Long> actualIds = specRepository.getAllIds();
    __THEN();
    assertThat(actualIds).isEqualTo(expectedIds);
  }

  @Test
  void existsByIdTest() {
    __GIVEN();
    Long id = TestDataStorage.getRandomItem(TestDataStorage.getTestSpecs()).getId();
    __WHEN();
    boolean isExists = specRepository.existsById(id);
    __THEN();
    assertThat(isExists).isTrue();
  }
}
