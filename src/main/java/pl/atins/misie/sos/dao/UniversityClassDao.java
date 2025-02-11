package pl.atins.misie.sos.dao;

import pl.atins.misie.sos.model.Subject;
import pl.atins.misie.sos.model.UniversityClass;

import java.util.List;

public interface UniversityClassDao {

     UniversityClass save(UniversityClass uc);
    void update(UniversityClass uc);
    void delete(UniversityClass uc);
    void deleteAll();
    UniversityClass findById(Integer id);
    List<UniversityClass> findAll();
    List<UniversityClass> findBySubject(Subject subject);

}
