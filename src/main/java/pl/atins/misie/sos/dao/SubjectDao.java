package pl.atins.misie.sos.dao;

import pl.atins.misie.sos.model.Subject;

import java.util.List;

public interface SubjectDao {
    <S extends Subject> S save(S subject);
    void update(Subject subject);
    void delete(Subject subject);
    void deleteAll();
    Subject findById(Integer id);
    List<Subject> findAll();
    List<Subject> findBySemester(String semester);
    List<Subject> findByAcademicYear(Integer academicYear);
    List<Subject> findActiveSubjects();
    List<Subject> findByLecturerId(int lecturerId);
}
