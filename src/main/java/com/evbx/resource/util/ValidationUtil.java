package com.evbx.resource.util;

import com.evbx.resource.exception.ItemAlreadyPresentException;
import com.evbx.resource.layer.repository.projection.NameProjection;

import java.util.List;
import java.util.Objects;

/**
 * Utility for Entity validations
 */
public final class ValidationUtil {

    private ValidationUtil() {

    }

    public static void validateResourceName(String name, List<NameProjection> projections) {
        for (NameProjection nameProjection : projections) {
            String projectionName = nameProjection.getName();
            if (Objects.nonNull(projectionName) && projectionName.equals(name)) {
                throw new ItemAlreadyPresentException(name);
            }
        }
    }
}
