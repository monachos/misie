package pl.atins.misie.sos.dao;

import pl.atins.misie.sos.model.Role;

import java.util.List;

public interface RoleDao {
    List<Role> findAll();

    void save(Role role);

    void delete(Role role);

    void update(Role role);

    void deleteAll();
}
