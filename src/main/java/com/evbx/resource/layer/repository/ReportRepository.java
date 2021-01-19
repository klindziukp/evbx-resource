package com.evbx.resource.layer.repository;

import com.evbx.resource.layer.repository.projection.NameProjection;
import com.evbx.resource.model.domain.IndustryReport;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface ReportRepository extends BaseRepository {

  List<IndustryReport> findAll();

  Optional<IndustryReport> findById(long id);

  IndustryReport save(IndustryReport industryReport);

  @Override
  @Query(value = "SELECT industry_name AS name FROM industry_report", nativeQuery = true)
  List<NameProjection> getNames();

  @Override
  @Query(value = "SELECT id FROM industry_report", nativeQuery = true)
  List<Long> getAllIds();
}
