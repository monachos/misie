package pl.atins.misie.sos.dao;

import pl.atins.misie.sos.model.Grade;
import pl.atins.misie.sos.model.User;

import java.util.List;

public interface GradeDao {
    List<Grade> findAll();
    void save(Grade grade);
    void delete(Grade grade);
    void update(Grade grade);
    void deleteAll();
    Grade findById(Integer id);
    List<Grade> findByUser(User user);
}
