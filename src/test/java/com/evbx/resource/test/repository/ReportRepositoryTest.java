package com.evbx.resource.test.repository;

import static com.evbx.resource.support.Step.__GIVEN;
import static com.evbx.resource.support.Step.__THEN;
import static com.evbx.resource.support.Step.__WHEN;
import static org.assertj.core.api.Assertions.assertThat;

import com.evbx.resource.constant.Item;
import com.evbx.resource.data.TestDataProjectionStorage;
import com.evbx.resource.data.TestDataStorage;
import com.evbx.resource.exception.ItemNotFoundException;
import com.evbx.resource.layer.repository.ReportRepository;
import com.evbx.resource.layer.repository.projection.NameProjection;
import com.evbx.resource.model.domain.IndustryReport;
import com.evbx.resource.support.Ignore;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ReportRepositoryTest extends BaseRepositoryTest {

  private ReportRepository reportRepository;

  @Autowired
  public ReportRepositoryTest(ReportRepository reportRepository) {
    this.reportRepository = reportRepository;
  }

  @Test
  void findAllIndustryReportsTest() {
    __GIVEN();
    List<IndustryReport> expectedIndustryReports = TestDataStorage.getTestIndustryReports();
    __WHEN();
    List<IndustryReport> actualIndustryReports = reportRepository.findAll();
    __THEN();
    assertThat(actualIndustryReports)
        .usingElementComparatorIgnoringFields(Ignore.getUpdatableEntityFields())
        .isEqualTo(expectedIndustryReports);
  }

  @Test
  void findIndustryReportByIdTest() {
    __GIVEN();
    IndustryReport expectedIndustryReport =
        TestDataStorage.getRandomItem(TestDataStorage.getTestIndustryReports());
    __WHEN();
    Long id = expectedIndustryReport.getId();
    IndustryReport actualIndustryReport =
        reportRepository
            .findById(id)
            .orElseThrow(() -> new ItemNotFoundException(Item.INDUSTRY_REPORT, id));
    __THEN();
    assertThat(actualIndustryReport)
        .isEqualToIgnoringGivenFields(expectedIndustryReport, Ignore.getUpdatableEntityFields());
  }

  @Test
  void saveIndustryReportTest() {
    __GIVEN();
    IndustryReport mutationIndustryReport = TestDataStorage.getMutationIndustryReport();
    __WHEN();
    IndustryReport savedIndustryReport = reportRepository.save(mutationIndustryReport);
    Long id = savedIndustryReport.getId();
    IndustryReport extractedIndustryReport =
        reportRepository
            .findById(id)
            .orElseThrow(() -> new ItemNotFoundException(Item.INDUSTRY_REPORT, id));
    __THEN();
    assertThat(mutationIndustryReport)
        .isEqualToIgnoringGivenFields(savedIndustryReport, Ignore.getUpdatableEntityFields());
    assertThat(mutationIndustryReport)
        .isEqualToIgnoringGivenFields(extractedIndustryReport, Ignore.getUpdatableEntityFields());
  }

  @Test
  void updateIndustryReportTest() {
    __GIVEN();
    IndustryReport mutationIndustryReport = TestDataStorage.getMutationIndustryReport();
    IndustryReport expectedIndustryReport =
        TestDataStorage.getRandomItem(TestDataStorage.getTestIndustryReports());
    expectedIndustryReport.setIndustryName(mutationIndustryReport.getIndustryName());
    expectedIndustryReport.setText(mutationIndustryReport.getText());
    int itemsSizeBefore = reportRepository.findAll().size();
    __WHEN();
    IndustryReport savedIndustryReport = reportRepository.save(expectedIndustryReport);
    Long id = savedIndustryReport.getId();
    int itemsSizeAfter = reportRepository.findAll().size();
    IndustryReport extractedIndustryReport =
        reportRepository
            .findById(id)
            .orElseThrow(() -> new ItemNotFoundException(Item.INDUSTRY_REPORT, id));
    __THEN();
    assertThat(extractedIndustryReport)
        .isEqualToIgnoringGivenFields(expectedIndustryReport, Ignore.getUpdatableEntityFields());
    assertThat(itemsSizeAfter).isEqualTo(itemsSizeBefore);
  }

  @Test
  void deleteByIdIndustryReportTest() {
    __GIVEN();
    List<IndustryReport> IndustryReportsBeforeDelete = reportRepository.findAll();
    __WHEN();
    IndustryReport IndustryReportToDelete =
        TestDataStorage.getRandomItem(IndustryReportsBeforeDelete);
    reportRepository.softDelete(IndustryReportToDelete.getId(), "script");
    __THEN();
    List<IndustryReport> IndustryReportsAfterDelete = reportRepository.findAll();
    assertThat(IndustryReportsAfterDelete).doesNotContain(IndustryReportToDelete);
  }

  @Test
  void getIndustryReportNamesProjectionTest() {
    __GIVEN();
    List<NameProjection> expectedIndustryReportNameProjections =
        TestDataProjectionStorage.getIndustryReportNameProjections();
    __WHEN();
    List<NameProjection> actualIndustryReportNameProjections = reportRepository.getNames();
    __THEN();
    projectionVerificationService.verifyNameProjections(
        actualIndustryReportNameProjections, expectedIndustryReportNameProjections);
  }

  @Test
  void getIndustryReportIdsTest() {
    __GIVEN();
    List<Long> expectedIds = TestDataStorage.getIdsFromList(TestDataStorage.getTestSpecs());
    __WHEN();
    List<Long> actualIds = reportRepository.getAllIds();
    __THEN();
    assertThat(actualIds).isEqualTo(expectedIds);
  }

  @Test
  void existsByIdTest() {
    __GIVEN();
    Long id = TestDataStorage.getRandomItem(TestDataStorage.getTestSpecs()).getId();
    __WHEN();
    boolean isExists = reportRepository.existsById(id);
    __THEN();
    assertThat(isExists).isTrue();
  }
}
