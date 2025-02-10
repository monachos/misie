package pl.atins.misie.sos.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import pl.atins.misie.sos.dao.SubjectDao;
import pl.atins.misie.sos.model.Subject;
import pl.atins.misie.sos.model.User;

import java.util.List;

@Transactional
public class SubjectDaoImpl implements SubjectDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Subject subject) {
        entityManager.persist(subject);
    }

    @Override
    public void update(Subject subject) {
        entityManager.merge(subject);
    }

    @Override
    public void delete(Subject subject) {
        entityManager.remove(entityManager.contains(subject) ? subject : entityManager.merge(subject));
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Subject").executeUpdate();
    }

    @Override
    public Subject findById(Integer id) {
        return entityManager.find(Subject.class, id);
    }

    @Override
    public List<Subject> findAll() {
        return entityManager.createQuery("SELECT s FROM Subject s", Subject.class).getResultList();
    }

    @Override
    public List<Subject> findBySemester(String semester) {
        return entityManager.createQuery("SELECT s FROM Subject s WHERE s.semester = :semester", Subject.class)
                .setParameter("semester", semester)
                .getResultList();
    }

    @Override
    public List<Subject> findByAcademicYear(Integer academicYear) {
        return entityManager.createQuery("SELECT s FROM Subject s WHERE s.academicYear = :academicYear", Subject.class)
                .setParameter("academicYear", academicYear)
                .getResultList();
    }

    @Override
    public List<Subject> findActiveSubjects() {
        return entityManager.createQuery("SELECT s FROM Subject s WHERE s.isActive = true", Subject.class).getResultList();
    }

    @Override
    public List<Subject> findByLecturer(User lecturer) {
        return entityManager.createQuery("SELECT s FROM Subject s WHERE s.lecturer = :lecturer", Subject.class)
                .setParameter("lecturer", lecturer)
                .getResultList();
    }
}
