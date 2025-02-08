package pl.atins.misie.sos.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import pl.atins.misie.sos.dao.RoleDao;
import pl.atins.misie.sos.model.Role;

import java.util.List;

public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Role> findAll() {
        final String hql = "SELECT R FROM Role R";
        final TypedQuery<Role> query = entityManager.createQuery(hql, Role.class);
        return query.getResultList();
    }

    @Override
    public void save(Role role) {
        entityManager.persist(role);
    }

    @Override
    public void delete(Role role) {
        entityManager.remove(role);
    }

    @Override
    public void update(Role role) {
        entityManager.merge(role);
    }

    @Override
    public void deleteAll() {
        final String hql = "DELETE FROM Role R";
        final Query query = entityManager.createQuery(hql);
        query.executeUpdate();
    }
}
