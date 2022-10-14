package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao{
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public Set<Role> findRoleById(List<Long> ids) {
        HashSet<Role> set = new HashSet<>();
        for (Long id:ids) {
            set.add((Role)entityManager.createQuery("from Role r where r.id = :id").setParameter("id",id).getSingleResult());
        }
        return set;
    }
}
