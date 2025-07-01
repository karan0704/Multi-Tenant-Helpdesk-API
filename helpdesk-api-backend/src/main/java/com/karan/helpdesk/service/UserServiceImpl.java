package com.karan.helpdesk.service.impl;

import com.karan.helpdesk.dto.UserDTO;
import com.karan.helpdesk.entity.User;
import com.karan.helpdesk.repository.UserRepository;
import com.karan.helpdesk.security.AuthUtil;
import com.karan.helpdesk.security.TenantContext;
import com.karan.helpdesk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO getCurrentUser() {
        User user = AuthUtil.getAuthenticatedUser();
        return toDTO(user);
    }

    @Override
    public List<UserDTO> getEngineersByTenant(String tenantId) {
        List<User> engineers = userRepository.findByTenantIdAndRole(UUID.fromString(tenantId), User.Role.ENGINEER);
        return engineers.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllUsersForCurrentTenant() {
        UUID tenantId = TenantContext.getTenantId();
        List<User> users = userRepository.findByTenantId(tenantId);
        return users.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
