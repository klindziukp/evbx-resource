package com.evbx.resource.exception;

import com.evbx.resource.constant.Item;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(Item item, Long id) {
        super(String.format("'%s' item not found with id = %d", item.getItemName(), id));
    }
}
