package com.evbx.resource.layer.repository.impl;

import com.evbx.resource.layer.repository.ReportRepository;
import com.evbx.resource.model.domain.IndustryReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportJpaRepository
    extends JpaRepository<IndustryReport, Long>, ReportRepository {}
