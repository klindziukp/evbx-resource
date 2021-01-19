package com.evbx.resource.layer.service;

import java.util.List;

public interface BaseService {

  List<Long> getAllIds();

  void deleteById(long id);
}
