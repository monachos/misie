package pl.atins.misie.sos.dao;

import jakarta.transaction.Transactional;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.atins.misie.sos.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@Transactional
@Rollback(true)
public class StudentSubjectDaoTest {

    @Autowired
    private StudentSubjectDao studentSubjectDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private DepartmentDao departmentDao;

    private User createSampleUser(Consumer<User> customizationAction) {
        Address address = new Address();
        address.setAddressLine1("Test Street 123");
        addressDao.save(address);

        final User user = SampleData.newUser(u -> {
            u.setRegisteredAddress(address);
            customizationAction.accept(u);
        });
        return userDao.save(user);
    }

    private Subject createSubject() {
        final var subject = SampleData.newSubject(s -> {
            s.setDepartment(createDepartment());
        });
        return subjectDao.save(subject);
    }

    private Department createDepartment() {
        Department department = new Department();
        department.setName("Computer Science");
        departmentDao.save(department);
        return department;
    }

    private StudentSubject createStudentSubject() {
        StudentSubject studentSubject = new StudentSubject();
        studentSubject.setStudent(createSampleUser(u -> {}));
        studentSubject.setSubject(createSubject());
        studentSubject.setRegistrationTime(LocalDateTime.now());

        studentSubjectDao.save(studentSubject);
        return studentSubject;
    }

    @Test
    public void testFindAll() {
        Assert.assertEquals(0, studentSubjectDao.findAll().size());

        createStudentSubject();
        Assert.assertEquals(1, studentSubjectDao.findAll().size());

        createStudentSubject();
        Assert.assertEquals(2, studentSubjectDao.findAll().size());

        studentSubjectDao.deleteAll();
        Assert.assertEquals(0, studentSubjectDao.findAll().size());
    }

    @Test
    public void testSave() {
        StudentSubject studentSubject = createStudentSubject();

        List<StudentSubject> studentSubjects = studentSubjectDao.findAll();
        Assert.assertEquals(1, studentSubjects.size());

        StudentSubject savedStudentSubject = studentSubjects.get(0);
        Assert.assertNotNull(savedStudentSubject.getStudent());
        Assert.assertNotNull(savedStudentSubject.getSubject());
        Assert.assertNotNull(savedStudentSubject.getRegistrationTime());
    }

    @Test
    public void testUpdate() {
        StudentSubject studentSubject = createStudentSubject();
        studentSubject.setRegistrationTime(LocalDateTime.now().plusDays(2));
        studentSubjectDao.update(studentSubject);

        StudentSubject updatedStudentSubject = studentSubjectDao.findById(studentSubject.getId());
        Assert.assertEquals(studentSubject.getRegistrationTime(), updatedStudentSubject.getRegistrationTime());
    }

    @Test
    public void testDelete() {
        StudentSubject studentSubject = createStudentSubject();
        studentSubjectDao.delete(studentSubject);
        Assert.assertNull(studentSubjectDao.findById(studentSubject.getId()));
    }

    @Test
    public void testDeleteAll() {
        for (int i = 0; i < 5; i++) {
            createStudentSubject();
        }

        studentSubjectDao.deleteAll();
        Assert.assertEquals(0, studentSubjectDao.findAll().size());
    }

    @Test
    public void testFindByStudent() {
        final var mainStudent = createSampleUser(u -> {
        });
        for (int i = 0; i < 5; i++) {
            final var studentSubject = new StudentSubject();
            studentSubject.setStudent(mainStudent);
            studentSubject.setSubject(createSubject());
            studentSubject.setRegistrationTime(LocalDateTime.now());
            studentSubjectDao.save(studentSubject);
        }
        final var otherStudent = createSampleUser(u -> {
        });
        for (int i = 0; i < 3; i++) {
            final var studentSubject = new StudentSubject();
            studentSubject.setStudent(otherStudent);
            studentSubject.setSubject(createSubject());
            studentSubject.setRegistrationTime(LocalDateTime.now());
            studentSubjectDao.save(studentSubject);
        }

        final List<StudentSubject> result = studentSubjectDao.findByStudent(mainStudent);
        Assert.assertEquals(5, result.size());
    }

    @Test
    public void testFindBySubject() {
        final var mainSubject = createSubject();
        for (int i = 0; i < 5; i++) {
            final var studentSubject = new StudentSubject();
            studentSubject.setStudent(createSampleUser(u -> {}));
            studentSubject.setSubject(mainSubject);
            studentSubject.setRegistrationTime(LocalDateTime.now());
            studentSubjectDao.save(studentSubject);
        }
        final var otherSubject = createSubject();
        for (int i = 0; i < 3; i++) {
            final var studentSubject = new StudentSubject();
            studentSubject.setStudent(createSampleUser(u -> {}));
            studentSubject.setSubject(otherSubject);
            studentSubject.setRegistrationTime(LocalDateTime.now());
            studentSubjectDao.save(studentSubject);
        }

        final List<StudentSubject> result = studentSubjectDao.findBySubject(mainSubject);
        Assert.assertEquals(5, result.size());
    }
}
