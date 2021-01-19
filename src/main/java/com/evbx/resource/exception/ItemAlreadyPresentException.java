package com.evbx.resource.exception;

public class ItemAlreadyPresentException extends RuntimeException {

  public ItemAlreadyPresentException(String name) {
    super("Item already presents with name = " + name);
  }
}
