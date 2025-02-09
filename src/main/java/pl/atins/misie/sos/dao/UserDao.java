package pl.atins.misie.sos.dao;

import pl.atins.misie.sos.model.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findByEmail(String email);

    <S extends User> S save(S entity);

    boolean existsById(Integer id);

    long count();

    void deleteById(Integer id);

    void delete(User entity);

    Optional<User> findById(Integer id);

}
