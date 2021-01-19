package com.evbx.resource.model.data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.Data;

@Data
public class ItemData<T> implements Serializable {

  @SuppressWarnings("squid:S1948")
  private List<T> items;

  private long total;

  public ItemData(List<T> items) {
    this.items = Optional.ofNullable(items).orElse(Collections.emptyList());
    this.total = this.items.size();
  }
}
