package com.karan.helpdesk.security;

import com.karan.helpdesk.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {
    public static User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
