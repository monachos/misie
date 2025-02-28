package pl.atins.misie.sos.dao;

import pl.atins.misie.sos.model.Lending;
import pl.atins.misie.sos.model.User;

import java.util.List;

public interface LendingDao {
    List<Lending> findAll();
    void save(Lending lending);
    void delete(Lending lending);
    void update(Lending lending);
    void deleteAll();
    Lending findById(Integer id);
    long getCountByUser(User user);
}
