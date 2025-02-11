package pl.atins.misie.sos.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import pl.atins.misie.sos.dao.LendingDao;
import pl.atins.misie.sos.model.Lending;
import pl.atins.misie.sos.model.User;

import java.util.List;

@Transactional
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
        if (!entityManager.contains(lending)) {
            lending = entityManager.merge(lending);
        }
        entityManager.remove(lending);
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

    @Override
    public long getCountByUser(User user) {
        final String hql = "SELECT COUNT(L) FROM Lending L" +
                " WHERE L.user = :user";
        return entityManager.createQuery(hql, Long.class)
                .setParameter("user", user)
                .getSingleResult();
    }
}
