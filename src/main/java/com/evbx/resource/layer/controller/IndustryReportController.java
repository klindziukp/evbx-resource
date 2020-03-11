package com.evbx.resource.layer.controller;

import com.evbx.resource.layer.service.ReportService;
import com.evbx.resource.model.domain.IndustryReport;
import com.evbx.resource.model.data.ItemData;
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

import javax.validation.Valid;
import java.util.List;

@RestController
public class IndustryReportController {

    private ReportService reportService;

    @Autowired
    public IndustryReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(value = "/industry-reports/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IndustryReport> getReport(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.findById(id));
    }

    @GetMapping(value = "/industry-reports", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemData<IndustryReport>> getAllReports() {
        return ResponseEntity.ok(reportService.findAllReports());
    }

    @GetMapping(value = "/industry-reports/ids", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Long>> getAllReportsIds() {
        return ResponseEntity.ok(reportService.getAllIds());
    }

    @PostMapping(value = "/industry-reports", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addReport(@Valid @RequestBody IndustryReport industryReport) {
        return ResponseEntity.ok(reportService.save(industryReport));
    }

    @PatchMapping(value = "/industry-reports/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public IndustryReport updateReport(@PathVariable long id, @RequestBody IndustryReport industryReport) {
        return reportService.update(id, industryReport);
    }

    @DeleteMapping(value = "/industry-reports/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteReport(@PathVariable long id) {
        reportService.deleteById(id);
        return ResponseEntity.ok("Deleted item with id = " + id);
    }
}
