package pl.atins.misie.sos.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import pl.atins.misie.sos.dao.PaymentDao;
import pl.atins.misie.sos.model.Payment;

import java.util.List;

@Transactional
public class PaymentDaoImpl implements PaymentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Payment> findAll() {
        String hql = "SELECT P FROM Payment P";
        TypedQuery<Payment> query = entityManager.createQuery(hql, Payment.class);
        return query.getResultList();
    }

    @Override
    public void save(Payment payment) {
        entityManager.persist(payment);
    }

    @Override
    public void delete(Payment payment) {
        if (!entityManager.contains(payment)) {
            payment = entityManager.merge(payment);
        }
        entityManager.remove(payment);
    }

    @Override
    public void update(Payment payment) {
        entityManager.merge(payment);
    }

    @Override
    public void deleteAll() {
        String hql = "DELETE FROM Payment P";
        Query query = entityManager.createQuery(hql);
        query.executeUpdate();
    }
}
