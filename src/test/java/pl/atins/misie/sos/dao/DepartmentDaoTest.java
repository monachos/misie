package pl.atins.misie.sos.dao;

import jakarta.persistence.EntityExistsException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.atins.misie.sos.model.Department;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@Transactional
@Rollback(true)
public class DepartmentDaoTest {
    @Autowired
    DepartmentDao departmentDao;

    private Department assertAndGetExactlyOneRecord() {
        final List<Department> result = departmentDao.findAll();
        Assert.assertEquals(1, result.size());
        return result.get(0);
    }

    private void assertExactlyNoRecords() {
        final List<Department> result = departmentDao.findAll();
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testFindAllWhenEmpty() {
        assertExactlyNoRecords();
    }

    @Test
    public void testFindAllWithOne() {
        final var department = new Department();
        department.setName("MyDep");
        departmentDao.save(department);

        final Department result = assertAndGetExactlyOneRecord();
        Assert.assertEquals("MyDep", result.getName());
    }

    @Test
    public void testSave() {
        final var department = new Department();
        department.setName("NewDep");
        departmentDao.save(department);

        final Department result = assertAndGetExactlyOneRecord();
        Assert.assertTrue(result.getId() > 0);
        Assert.assertEquals("NewDep", result.getName());
    }

    @Test
    public void testSaveNonUniqueId() {
        final var department1 = new Department();
        department1.setName("Dep1");
        departmentDao.save(department1);

        final var department2 = new Department();
        department2.setId(department1.getId());
        department2.setName("Dep2");
        Assert.assertThrows(EntityExistsException.class,() -> departmentDao.save(department2));
    }

    @Test
    public void testUpdate() {
        final var department = new Department();
        department.setName("Dep");
        departmentDao.save(department);

        final Department existingDep = assertAndGetExactlyOneRecord();
        existingDep.setName("NewDep");
        departmentDao.update(existingDep);

        final Department result = assertAndGetExactlyOneRecord();
        Assert.assertEquals("NewDep", result.getName());
    }

    @Test
    public void testDelete() {
        final var department = new Department();
        department.setName("Dep");
        departmentDao.save(department);

        final Department existingDep = assertAndGetExactlyOneRecord();
        departmentDao.delete(existingDep);

        assertExactlyNoRecords();
    }

    @Test
    public void testDeleteAll() {
        for (int i = 0; i< 5; ++i) {
            final var department = new Department();
            department.setName("Dep_%d".formatted(i));
            departmentDao.save(department);
        }

        departmentDao.deleteAll();

        assertExactlyNoRecords();
    }
}
