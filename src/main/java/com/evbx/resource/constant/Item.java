package com.evbx.resource.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** Represents Resource items */
@RequiredArgsConstructor
@Getter
public enum Item {
  E_BOOK("E-Book"),
  INDUSTRY_REPORT("Industry report"),
  SPECIFICATION("Specification");

  private final String itemName;
}
