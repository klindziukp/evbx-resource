package com.evbx.resource.layer.repository;

import com.evbx.resource.layer.repository.projection.NameProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface BaseRepository {

    List<Long> getAllIds();
    List<NameProjection> getNames();
    boolean existsById(long id);

    @Query("UPDATE #{#entityName} entity SET entity.deletedAt = CURRENT_TIMESTAMP, entity.deletedBy = :deletedBy  WHERE entity.id=:id")
    @Modifying(clearAutomatically = true)
    @Transactional
    void softDelete(@Param("id")Long id, @Param("deletedBy")String deletedBy);
}
