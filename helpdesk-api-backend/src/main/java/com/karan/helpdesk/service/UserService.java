package com.karan.helpdesk.service;

import com.karan.helpdesk.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsersForCurrentTenant();
}
