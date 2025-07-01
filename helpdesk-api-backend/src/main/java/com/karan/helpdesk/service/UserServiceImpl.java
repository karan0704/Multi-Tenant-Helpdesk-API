package com.karan.helpdesk.service.impl;

import com.karan.helpdesk.entity.User;
import com.karan.helpdesk.repository.UserRepository;
import com.karan.helpdesk.tenant.TenantContext;
import com.karan.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsersForCurrentTenant() {
        UUID tenantId = TenantContext.getTenantId();
        return userRepository.findAll().stream()
                .filter(user -> user.getTenant().getId().equals(tenantId))
                .toList();
    }
}
