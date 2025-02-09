package pl.atins.misie.sos.dao;

import pl.atins.misie.sos.model.Department;

import java.util.List;

public interface DepartmentDao {
    List<Department> findAll();

    void save(Department department);

    void delete(Department department);

    void update(Department department);

    void deleteAll();
}
