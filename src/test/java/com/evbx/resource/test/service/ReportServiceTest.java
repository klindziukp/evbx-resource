package com.evbx.resource.test.service;

import com.evbx.resource.data.TestDataStorage;
import com.evbx.resource.exception.ItemNotFoundException;
import com.evbx.resource.layer.service.ReportService;
import com.evbx.resource.model.domain.IndustryReport;
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

class ReportServiceTest extends BaseServiceTest {

    private ReportService reportService;

    @Autowired
    public ReportServiceTest(ReportService reportService) {
        this.reportService = reportService;
    }

    @Test
    void findAllIndustryReportsTest() {
        __GIVEN();
        List<IndustryReport> expectedIndustryReports = TestDataStorage.getTestIndustryReports();
        __WHEN();
        List<IndustryReport> actualIndustryReports = reportService.findAllReports().getItems();
        __THEN();
        assertThat(actualIndustryReports).usingElementComparatorIgnoringFields(Ignore.getUpdatableEntityFields())
                .isEqualTo(expectedIndustryReports);
    }

    @Test
    void findIndustryReportByIdTest() {
        __GIVEN();
        IndustryReport expectedIndustryReport = TestDataStorage.getRandomItem(TestDataStorage.getTestIndustryReports());
        __WHEN();
        Long id = expectedIndustryReport.getId();
        IndustryReport actualIndustryReport = reportService.findById(id);
        __THEN();
        assertThat(actualIndustryReport).isEqualToIgnoringGivenFields(expectedIndustryReport,
                Ignore.getUpdatableEntityFields());
    }

    @Test
    void saveIndustryReportTest() {
        __GIVEN();
        mockSecurityContext();
        IndustryReport mutationIndustryReport = TestDataStorage.getMutationIndustryReport();
        __WHEN();
        IndustryReport savedIndustryReport = reportService.save(mutationIndustryReport);
        Long id = savedIndustryReport.getId();
        IndustryReport extractedIndustryReport = reportService.findById(id);
        __THEN();
        assertThat(mutationIndustryReport).isEqualToIgnoringGivenFields(savedIndustryReport,
                Ignore.getUpdatableEntityFields());
        assertThat(mutationIndustryReport).isEqualToIgnoringGivenFields(extractedIndustryReport,
                Ignore.getUpdatableEntityFields());
    }

    @Test
    void updateIndustryReportTest() {
        __GIVEN();
        mockSecurityContext();
        IndustryReport mutationIndustryReport = TestDataStorage.getMutationIndustryReport();
        IndustryReport expectedIndustryReport = TestDataStorage.getRandomItem(TestDataStorage.getTestIndustryReports());
        expectedIndustryReport.setIndustryName(mutationIndustryReport.getIndustryName());
        expectedIndustryReport.setText(mutationIndustryReport.getText());
        long totalBefore = reportService.findAllReports().getTotal();
        __WHEN();
        IndustryReport savedIndustryReport = reportService.save(expectedIndustryReport);
        Long id = savedIndustryReport.getId();
        long totalAfter = reportService.findAllReports().getTotal();
        IndustryReport extractedIndustryReport = reportService.findById(id);
        __THEN();
        assertThat(extractedIndustryReport).isEqualToIgnoringGivenFields(expectedIndustryReport,
                Ignore.getUpdatableEntityFields());
        assertThat(totalAfter).isEqualTo(totalBefore);
    }

    @Test
    void getAllIdsIndustryReportTest() {
        __GIVEN();
        List<Long> expectedIds = TestDataStorage.getIdsFromList(TestDataStorage.getTestIndustryReports());
        __WHEN();
        List<Long> actualIds = reportService.getAllIds();
        __THEN();
        assertThat(actualIds).isEqualTo(expectedIds);
    }

    @Test
    void deleteIndustryReportByIdTest() {
        __GIVEN();
        List<IndustryReport> IndustryReportsBeforeDelete = reportService.findAllReports().getItems();
        __WHEN();
        IndustryReport IndustryReportToDelete = TestDataStorage.getRandomItem(IndustryReportsBeforeDelete);
        reportService.deleteById(IndustryReportToDelete.getId());
        __THEN();
        List<IndustryReport> IndustryReportsAfterDelete = reportService.findAllReports().getItems();
        assertThat(IndustryReportsAfterDelete).doesNotContain(IndustryReportToDelete);
    }

    @Test
    void itemNotFoundExceptionGetTest() {
        __GIVEN();
        List<Long> ids = reportService.getAllIds();
        __WHEN();
        long nonPresentId = Collections.max(ids) + 100L;
        __THEN();
        Assertions.assertThrows(ItemNotFoundException.class, () -> reportService.findById(nonPresentId));
    }

    @Test
    void itemNotFoundExceptionUpdateTest() {
        __GIVEN();
        List<Long> ids = reportService.getAllIds();
        __WHEN();
        long nonPresentId = Collections.max(ids) + 101L;
        __THEN();
        Assertions.assertThrows(ItemNotFoundException.class,
                () -> reportService.update(nonPresentId, new IndustryReport()));
    }

    @Test
    void itemNotFoundExceptionDeleteTest() {
        __GIVEN();
        List<Long> ids = reportService.getAllIds();
        __WHEN();
        long nonPresentId = Collections.max(ids) + 102L;
        __THEN();
        Assertions.assertThrows(ItemNotFoundException.class, () -> reportService.deleteById(nonPresentId));
    }
}