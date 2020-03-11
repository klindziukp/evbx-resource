package com.evbx.resource.layer.service;

import com.evbx.resource.model.domain.IndustryReport;
import com.evbx.resource.model.data.ItemData;

public interface ReportService extends BaseService {

    ItemData<IndustryReport> findAllReports();
    IndustryReport findById(long id);
    IndustryReport save(IndustryReport industryReport);
    IndustryReport update(long id, IndustryReport industryReport);
}
