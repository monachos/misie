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

    private User createStudent() {
        User student = new User();

        String randomEmail = "student" + System.currentTimeMillis() + "@test.com";
        student.setEmail(randomEmail);

        student.setActive(true);
        student.setDeleted(false);
        student.setAcceptedPrivacyPolicy(true);
        student.setAcceptedTermsOfUse(true);
        student.setPassword("password123");
        student.setName("Test");
        student.setSurname("User");
        student.setBlockedAccount(false);

        Address address = new Address();
        address.setAddressLine1("Test Street 123");

        addressDao.save(address);

        student.setRegisteredAddress(address);
        userDao.save(student);

        return student;
    }

    private Subject createSubject() {
        Subject subject = new Subject();
        subject.setTitle("Database Systems");
        subject.setDescription("Introduction to Databases");
        subject.setMaxStudents(30);
        subject.setIsActive(true);
        subject.setSemester("Spring");
        subject.setAcademicYear(2024);
        subject.setRegistrationStart(LocalDateTime.now().minusDays(10));
        subject.setRegistrationEnd(LocalDateTime.now().plusDays(10));
        subject.setRoomNumber("101");
        subject.setTime(LocalDateTime.now().plusDays(5));

        subject.setDepartment(createDepartment());

        subjectDao.save(subject);
        return subject;
    }

    private Department createDepartment() {
        Department department = new Department();
        department.setName("Computer Science");
        departmentDao.save(department);
        return department;
    }

    private StudentSubject createStudentSubject() {
        StudentSubject studentSubject = new StudentSubject();
        studentSubject.setStudent(createStudent());
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
}
