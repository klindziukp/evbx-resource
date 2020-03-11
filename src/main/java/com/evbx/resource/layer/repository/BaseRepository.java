package com.evbx.resource.layer.repository;

import com.evbx.resource.layer.repository.projection.NameProjection;

import java.util.List;

public interface BaseRepository {

    void deleteById(long id);
    List<Long> getAllIds();
    List<NameProjection> getNames();
    boolean existsById(long id);
}
