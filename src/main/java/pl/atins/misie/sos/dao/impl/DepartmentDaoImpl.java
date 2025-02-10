package pl.atins.misie.sos.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import pl.atins.misie.sos.dao.DepartmentDao;
import pl.atins.misie.sos.model.Department;

import java.util.List;

@Transactional
public class DepartmentDaoImpl implements DepartmentDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Department> findAll() {
        final String hql = "SELECT D FROM Department D";
        final TypedQuery<Department> query = entityManager.createQuery(hql, Department.class);
        return query.getResultList();
    }

    @Override
    public void save(Department department) {
        entityManager.persist(department);
    }

    @Override
    public void delete(Department department) {
        entityManager.remove(department);
    }

    @Override
    public void update(Department department) {
        entityManager.merge(department);
    }

    @Override
    public void deleteAll() {
        final String hql = "DELETE FROM Department D";
        final Query query = entityManager.createQuery(hql);
        query.executeUpdate();
    }
}
