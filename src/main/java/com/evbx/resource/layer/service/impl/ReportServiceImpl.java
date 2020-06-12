package com.evbx.resource.layer.service.impl;

import com.evbx.resource.constant.Item;
import com.evbx.resource.exception.ItemNotFoundException;
import com.evbx.resource.layer.repository.ReportRepository;
import com.evbx.resource.layer.service.ReportService;
import com.evbx.resource.model.domain.IndustryReport;
import com.evbx.resource.model.data.ItemData;
import com.evbx.resource.util.AuthUtil;
import com.evbx.resource.util.UpdateUtil;
import com.evbx.resource.util.ValidationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger LOGGER = LogManager.getLogger(ReportServiceImpl.class);

    private ReportRepository reportRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    @Cacheable(value= "allReportsCache", unless= "#result.getTotal() == 0")
    public ItemData<IndustryReport> findAllReports() {
        LOGGER.info("Get all reports");
        return new ItemData<>(reportRepository.findAll());
    }

    @Override
    @Cacheable(value= "reportCache", key= "'report'+#id")
    public IndustryReport findById(long id) {
        LOGGER.info("Get report with id = '{}'", id);
        return reportRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(Item.INDUSTRY_REPORT, id));
    }

    @Override
    @Caching(put = { @CachePut(value = "reportCache", key = "'report'+#industryReport.id") },
             evict = { @CacheEvict(value = "allReportsCache", allEntries = true) })
    public IndustryReport save(IndustryReport industryReport) {
        industryReport.setCreatedBy(AuthUtil.getUserName());
        verifyReportPresence(industryReport);
        IndustryReport savedReport = reportRepository.save(industryReport);
        LOGGER.info("Report with id = '{}' updated successfully", savedReport.getId());
        return savedReport;
    }

    @Override
    @Caching(put = { @CachePut(value = "reportCache", key = "'report'+#industryReport.id") },
             evict = { @CacheEvict(value = "allReportsCache", allEntries = true) })
    public IndustryReport update(long id, IndustryReport industryReport) {
        IndustryReport persistedIndustryReport = reportRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException(Item.INDUSTRY_REPORT, id));
        verifyReportPresence(industryReport);
        UpdateUtil.updateItem(industryReport, persistedIndustryReport);
        IndustryReport updatedReport = reportRepository.save(persistedIndustryReport);
        LOGGER.info("Report with id = '{}' updated successfully", id);
        return updatedReport;
    }

    @Override
    public List<Long> getAllIds() {
        return reportRepository.getAllIds();
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "reportCache", key = "'report'+#id"),
                       @CacheEvict(value = "allReportsCache", allEntries = true) })
    public void deleteById(long id) {
        if (!reportRepository.existsById(id)) {
            throw new ItemNotFoundException(Item.INDUSTRY_REPORT, id);
        }
        reportRepository.softDelete(id, AuthUtil.getUserName());
        LOGGER.info("Report with id = '{}' deleted successfully", id);
    }

    private void verifyReportPresence(IndustryReport industryReport) {
        String specificationName = industryReport.getIndustryName();
        if (Objects.nonNull(specificationName)) {
            ValidationUtil.validateResourceName(specificationName, reportRepository.getNames());
        }
    }
}
