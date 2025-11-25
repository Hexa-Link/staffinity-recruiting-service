package com.staffinity.recruiting.security.domain.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

/**
 * Domain model representing an authenticated user.
 * This is NOT a database entity. It's a domain object that represents
 * the user's identity extracted from the JWT token.
 * Used by the security layer to make authorization decisions.
 */
@Data
@Builder
public class AuthenticatedUser {

    /**
     * User ID from the Personal Service (Employee ID).
     */
    private String userId;

    /**
     * User's email address.
     */
    private String email;

    /**
     * User's role (e.g., RECRUITER, MANAGER, ADMIN).
     */
    private String role;

    /**
     * User's full name (optional, can be fetched from Personal Service if needed).
     */
    private String name;

    /**
     * Converts the role to Spring Security authorities.
     *
     * @return Collection of granted authorities
     */
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }
}