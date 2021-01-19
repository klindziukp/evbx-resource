package com.evbx.resource.layer.service;

import com.evbx.resource.model.data.ItemData;
import com.evbx.resource.model.domain.Specification;

public interface SpecService extends BaseService {

  ItemData<Specification> findAllSpecifications();

  Specification findById(long id);

  Specification save(Specification specification);

  Specification update(long id, Specification specification);
}
