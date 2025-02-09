package pl.atins.misie.sos.dao;

import pl.atins.misie.sos.model.StudentSubject;
import pl.atins.misie.sos.model.User;
import pl.atins.misie.sos.model.Subject;

import java.util.List;

public interface StudentSubjectDao {
    void save(StudentSubject studentSubject);
    void update(StudentSubject studentSubject);
    void delete(StudentSubject studentSubject);
    void deleteAll();
    StudentSubject findById(Integer id);
    List<StudentSubject> findAll();
    List<StudentSubject> findByStudent(User student);
    List<StudentSubject> findBySubject(Subject subject);
}
