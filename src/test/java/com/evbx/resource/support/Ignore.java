package com.evbx.resource.support;

public final class Ignore {

  private Ignore() {}

  public static String[] getUpdatableEntityFields() {
    return new String[] {"id", "createdAt", "updatedAt", "deletedAt", "updatedBy", "deletedBy"};
  }
}
