package pl.atins.misie.sos.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pl.atins.misie.sos.dao.GradeDao;
import pl.atins.misie.sos.model.Grade;

import java.util.List;

@Repository
public class GradeDaoImpl implements GradeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Grade> findAll() {
        String hql = "SELECT G FROM Grade G";
        TypedQuery<Grade> query = entityManager.createQuery(hql, Grade.class);
        return query.getResultList();
    }

    @Override
    public void save(Grade grade) {
        entityManager.persist(grade);
    }

    @Override
    public void delete(Grade grade) {
        entityManager.remove(entityManager.contains(grade) ? grade : entityManager.merge(grade));
    }

    @Override
    public void update(Grade grade) {
        entityManager.merge(grade);
    }

    @Override
    public void deleteAll() {
        String hql = "DELETE FROM Grade";
        Query query = entityManager.createQuery(hql);
        query.executeUpdate();
    }

    @Override
    public Grade findById(Integer id) {
        return entityManager.find(Grade.class, id);
    }
}
