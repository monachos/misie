package pl.atins.misie.sos.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pl.atins.misie.sos.dao.LendingDao;
import pl.atins.misie.sos.model.Lending;

import java.util.List;

@Repository
public class LendingDaoImpl implements LendingDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Lending> findAll() {
        String hql = "SELECT L FROM Lending L";
        TypedQuery<Lending> query = entityManager.createQuery(hql, Lending.class);
        return query.getResultList();
    }

    @Override
    public void save(Lending lending) {
        entityManager.persist(lending);
    }

    @Override
    public void delete(Lending lending) {
        entityManager.remove(entityManager.contains(lending) ? lending : entityManager.merge(lending));
    }

    @Override
    public void update(Lending lending) {
        entityManager.merge(lending);
    }

    @Override
    public void deleteAll() {
        String hql = "DELETE FROM Lending";
        Query query = entityManager.createQuery(hql);
        query.executeUpdate();
    }

    @Override
    public Lending findById(Integer id) {
        return entityManager.find(Lending.class, id);
    }
}
