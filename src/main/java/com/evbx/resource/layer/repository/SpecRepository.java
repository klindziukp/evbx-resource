package com.evbx.resource.layer.repository;

import com.evbx.resource.layer.repository.projection.NameProjection;
import com.evbx.resource.model.domain.Specification;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface SpecRepository extends BaseRepository {

  List<Specification> findAll();

  Optional<Specification> findById(long id);

  Specification save(Specification specification);

  @Override
  @Query(value = "SELECT specification_name AS name FROM specifications", nativeQuery = true)
  List<NameProjection> getNames();

  @Override
  @Query(value = "SELECT id FROM specifications", nativeQuery = true)
  List<Long> getAllIds();
}
