package pl.atins.misie.sos.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pl.atins.misie.sos.dao.UniversityClassDao;
import pl.atins.misie.sos.model.Subject;
import pl.atins.misie.sos.model.UniversityClass;
import pl.atins.misie.sos.model.UniversityClass;

import java.util.List;

public class UniversityClassDaoImpl implements UniversityClassDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UniversityClass save(UniversityClass universityClass) {
        entityManager.persist(universityClass);
        return universityClass;
    }

    @Override
    public void update(UniversityClass universityClass) {
        entityManager.merge(universityClass);
    }

    @Override
    public void delete(UniversityClass universityClass) {
        entityManager.remove(entityManager.contains(universityClass) ? universityClass : entityManager.merge(universityClass));
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM UniversityClass").executeUpdate();
    }

    @Override
    public UniversityClass findById(Integer id) {
        return entityManager.find(UniversityClass.class, id);
    }

    @Override
    public List<UniversityClass> findAll() {
        return entityManager.createQuery("SELECT s FROM UniversityClass s", UniversityClass.class).getResultList();
    }

    @Override
    public List<UniversityClass> findBySubject(Subject subject) {
        return entityManager.createQuery("SELECT s FROM UniversityClass s WHERE s.subject = :subject", UniversityClass.class)
                .setParameter("subject", subject)
                .getResultList();
    }


}
