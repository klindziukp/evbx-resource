package com.evbx.resource.layer.repository.projection;

/**
 * Projection to map only item names from repositories
 */
public interface NameProjection {

    String getName();
    void setName(String name);
}
