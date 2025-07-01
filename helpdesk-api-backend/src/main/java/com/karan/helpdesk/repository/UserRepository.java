package com.karan.helpdesk.repository;

import com.karan.helpdesk.entity.User;
import com.karan.helpdesk.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    List<User> findByTenant(Tenant tenant);
    
    List<User> findByTenantId(UUID tenantId);
    List<User> findByTenantIdAndRole(UUID tenantId, User.Role role);

}
