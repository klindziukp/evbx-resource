package com.evbx.resource.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utility for SecurityContext actions.
 */
public final class AuthUtil {

    private AuthUtil() {
    }

    /**
     * Returns authenticated user name
     */
    public static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
