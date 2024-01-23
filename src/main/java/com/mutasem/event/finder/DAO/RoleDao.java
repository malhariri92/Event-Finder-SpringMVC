package com.mutasem.event.finder.DAO;

import com.mutasem.event.finder.models.Role;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDao implements IRoleDao{
    private EntityManager entityManager;

    @Autowired
    public RoleDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public Role getRoleById(Integer id) {
        return entityManager.find(Role.class, id);
    }
}
