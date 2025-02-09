package pl.atins.misie.sos.dao;

import jakarta.persistence.EntityExistsException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.atins.misie.sos.model.Department;
import pl.atins.misie.sos.model.Role;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@Transactional
@Rollback(true)
public class RoleDaoTest {
    @Autowired
    RoleDao roleDao;

    @Autowired
    DepartmentDao departmentDao;

    private Department mainDepartment;

    private Role assertAndGetExactlyOneRecord() {
        final List<Role> result = roleDao.findAll();
        Assert.assertEquals(1, result.size());
        return result.get(0);
    }

    private void assertExactlyNoRecords() {
        final List<Role> result = roleDao.findAll();
        Assert.assertTrue(result.isEmpty());
    }

    @Before
    public void setUp() {
        mainDepartment = new Department();
        mainDepartment.setName("Main");
        departmentDao.save(mainDepartment);
    }

    @Test
    public void testFindAllWhenEmpty() {
        assertExactlyNoRecords();
    }

    @Test
    public void testFindAllWithOne() {
        {
            final var role = new Role();
            role.setDepartment(mainDepartment);
            role.setName("Admin");
            role.setIsAdmin(true);
            roleDao.save(role);
        }

        final Role result = assertAndGetExactlyOneRecord();
        Assert.assertEquals("Admin", result.getName());
        Assert.assertTrue(result.getIsAdmin());
    }

    @Test
    public void testSave() {
        {
            final var role = new Role();
            role.setDepartment(mainDepartment);
            role.setName("User");
            role.setIsAdmin(false);
            roleDao.save(role);
        }

        final Role result = assertAndGetExactlyOneRecord();
        Assert.assertTrue(result.getId() > 0);
        Assert.assertEquals("User", result.getName());
        Assert.assertFalse(result.getIsAdmin());
        Assert.assertNotNull(result.getDepartment());
        Assert.assertEquals("Main", result.getDepartment().getName());
    }

    @Test
    public void testSaveNonUniqueId() {
        final var role1 = new Role();
        role1.setDepartment(mainDepartment);
        role1.setName("Role1");
        role1.setIsAdmin(false);
        roleDao.save(role1);

        final var role2 = new Role();
        role2.setDepartment(mainDepartment);
        role2.setId(role1.getId());
        role2.setName("Role2");
        role2.setIsAdmin(false);
        Assert.assertThrows(EntityExistsException.class, () -> roleDao.save(role2));
    }

    @Test
    public void testUpdate() {
        {
            final var role = new Role();
            role.setDepartment(mainDepartment);
            role.setName("Student");
            role.setIsAdmin(false);
            roleDao.save(role);
        }

        {
            final Role existingRole = assertAndGetExactlyOneRecord();
            existingRole.setName("Admin");
            existingRole.setIsAdmin(true);
            roleDao.update(existingRole);
        }

        final Role result = assertAndGetExactlyOneRecord();
        Assert.assertEquals("Admin", result.getName());
        Assert.assertTrue(result.getIsAdmin());
    }

    @Test
    public void testDelete() {
        {
            final var role = new Role();
            role.setDepartment(mainDepartment);
            role.setName("Role");
            role.setIsAdmin(false);
            roleDao.save(role);
        }

        final Role existingRole = assertAndGetExactlyOneRecord();
        roleDao.delete(existingRole);

        assertExactlyNoRecords();
    }

    @Test
    public void testDeleteAll() {
        for (int i = 0; i< 5; ++i) {
            final var role = new Role();
            role.setDepartment(mainDepartment);
            role.setName("Role_%d".formatted(i));
            role.setIsAdmin(false);
            roleDao.save(role);
        }

        roleDao.deleteAll();

        assertExactlyNoRecords();
    }
}
