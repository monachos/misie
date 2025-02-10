package pl.atins.misie.sos.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import pl.atins.misie.sos.dao.StudentSubjectDao;
import pl.atins.misie.sos.model.StudentSubject;
import pl.atins.misie.sos.model.User;
import pl.atins.misie.sos.model.Subject;

import java.util.List;

@Transactional
public class StudentSubjectDaoImpl implements StudentSubjectDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(StudentSubject studentSubject) {
        entityManager.persist(studentSubject);
    }

    @Override
    public void update(StudentSubject studentSubject) {
        entityManager.merge(studentSubject);
    }

    @Override
    public void delete(StudentSubject studentSubject) {
        entityManager.remove(entityManager.contains(studentSubject) ? studentSubject : entityManager.merge(studentSubject));
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM StudentSubject").executeUpdate();
    }

    @Override
    public StudentSubject findById(Integer id) {
        return entityManager.find(StudentSubject.class, id);
    }

    @Override
    public List<StudentSubject> findAll() {
        return entityManager.createQuery("SELECT ss FROM StudentSubject ss", StudentSubject.class).getResultList();
    }

    @Override
    public List<StudentSubject> findByStudent(User student) {
        return entityManager.createQuery("SELECT ss FROM StudentSubject ss WHERE ss.student = :student", StudentSubject.class)
                .setParameter("student", student)
                .getResultList();
    }

    @Override
    public List<StudentSubject> findBySubject(Subject subject) {
        return entityManager.createQuery("SELECT ss FROM StudentSubject ss WHERE ss.subject = :subject", StudentSubject.class)
                .setParameter("subject", subject)
                .getResultList();
    }
}
