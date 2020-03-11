package com.evbx.resource.layer.service;

import com.evbx.resource.model.domain.Specification;
import com.evbx.resource.model.data.ItemData;

public interface SpecService extends BaseService {

    ItemData<Specification> findAllSpecifications();
    Specification findById(long id);
    Specification save(Specification specification);
    Specification update(long id, Specification specification);
}
