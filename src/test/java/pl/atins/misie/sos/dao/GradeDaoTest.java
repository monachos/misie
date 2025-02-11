package pl.atins.misie.sos.dao;

import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.atins.misie.sos.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml"})
@Transactional
@Rollback(true)
public class GradeDaoTest {

    @Autowired
    private GradeDao gradeDao;

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
        student.setEmail("student" + System.currentTimeMillis() + "@test.com");
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

    private StudentSubject createStudentSubject(Consumer<StudentSubject> customizationAction) {
        StudentSubject studentSubject = new StudentSubject();
        studentSubject.setStudent(createStudent());
        studentSubject.setSubject(createSubject());
        studentSubject.setRegistrationTime(LocalDateTime.of(2020, 5, 15, 12, 0));
        customizationAction.accept(studentSubject);
        studentSubjectDao.save(studentSubject);
        return studentSubject;
    }

    private Grade createGrade() {
        Grade grade = new Grade();
        grade.setStudentSubject(createStudentSubject());
        grade.setGrade(BigDecimal.valueOf(4.5));
        grade.setType("Exam");
        grade.setGradingDate(LocalDateTime.now());

        gradeDao.save(grade);
        return grade;
    }

    @Test
    public void testSave() {
        Grade grade = createGrade();
        Assert.assertNotNull(grade.getId());

        Grade retrievedGrade = gradeDao.findById(grade.getId());
        Assert.assertNotNull(retrievedGrade);
        Assert.assertEquals(BigDecimal.valueOf(4.5), retrievedGrade.getGrade());
        Assert.assertEquals(grade.getStudentSubject().getStudent().getEmail(), retrievedGrade.getStudentSubject().getStudent().getEmail());
    }

    @Test
    public void testFindAll() {
        Assert.assertEquals(0, gradeDao.findAll().size());

        createGrade();
        createGrade();

        List<Grade> grades = gradeDao.findAll();
        Assert.assertEquals(2, grades.size());
    }

    @Test
    public void testUpdate() {
        Grade grade = createGrade();
        grade.setGrade(BigDecimal.valueOf(5.0));
        gradeDao.update(grade);

        Grade updatedGrade = gradeDao.findById(grade.getId());
        Assert.assertEquals(BigDecimal.valueOf(5.0), updatedGrade.getGrade());
    }

    @Test
    public void testDelete() {
        Grade grade = createGrade();
        Integer id = grade.getId();

        gradeDao.delete(grade);
        Assert.assertNull(gradeDao.findById(id));
    }

    @Test
    public void testDeleteAll() {
        createGrade();
        createGrade();
        gradeDao.deleteAll();
        Assert.assertEquals(0, gradeDao.findAll().size());
    }

    @Test
    public void testFindByUserForNoData() {
        createGrade();
        createGrade();

        final List<Grade> result = gradeDao.findByUser(createStudent());

        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testFindByUserForExistingData() {
        final User student = createStudent();
        final var grade = new Grade();
        grade.setStudentSubject(createStudentSubject(ss -> ss.setStudent(student)));
        grade.setGrade(BigDecimal.valueOf(4.5));
        grade.setType("SpecialExam");
        grade.setGradingDate(LocalDateTime.of(2020, 5, 15, 12, 0));
        gradeDao.save(grade);
        createGrade();
        createGrade();

        final List<Grade> result = gradeDao.findByUser(student);

        Assert.assertEquals(1, result.size());
        Assert.assertEquals("SpecialExam", result.get(0).getType());
    }
}
