package pl.atins.misie.sos.dao;

import jakarta.persistence.EntityExistsException;
import org.assertj.core.api.SoftAssertions;
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
import pl.atins.misie.sos.model.Subject;
import pl.atins.misie.sos.model.UniversityClass;
import pl.atins.misie.sos.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@Transactional
@Rollback(true)
public class UniversityClassDaoTest {

    @Autowired
    UniversityClassDao universityClassDao;

    @Autowired
    SubjectDao subjectDao;

    @Autowired
    DepartmentDao departmentDao;

    private Department mainDepartment;

    private Subject sampleSubject;

    private UniversityClass assertAndGetExactlyOneRecord() {
        final List<UniversityClass> result = universityClassDao.findAll();
        Assert.assertEquals(1, result.size());
        return result.get(0);
    }

    private void assertExactlyNoRecords() {
        final List<UniversityClass> result = universityClassDao.findAll();
        Assert.assertTrue(result.isEmpty());
    }


    private Subject newSampleSubject(Consumer<Subject> customizationAction) {
        final var subject = new Subject();
        subject.setTime(LocalDateTime.of(2025, 1, 10, 15, 10, 0));
        subject.setDescription("Sample description");
        subject.setTitle("Algorytmy i struktury danych");
        subject.setMaxStudents(12);
        subject.setIsActive(true);
        subject.setSemester("VI");
        subject.setAcademicYear(2025);
        subject.setRegistrationStart(LocalDateTime.of(2025, 3, 1, 9, 0, 0));
        subject.setRegistrationEnd(LocalDateTime.of(2025, 3, 5, 17, 0, 0));
        subject.setRoomNumber("3.11");
        subject.setDepartment(mainDepartment);
        customizationAction.accept(subject);
        return subject;
    }

    private Subject createSampleSubject(Consumer<Subject> customizationAction) {
        final var subject = newSampleSubject(customizationAction);
        return subjectDao.save(subject);
    }

    @Before
    public void setUp()  {
        mainDepartment = new Department();
        mainDepartment.setName("Main");
        departmentDao.save(mainDepartment);

        sampleSubject = createSampleSubject( s->{} );
    }

    private UniversityClass createSampleUniversityClass(Consumer<UniversityClass> customizationAction) {

        final var universityClass = createNewUniversityClass(customizationAction);
        return universityClassDao.save(universityClass);

    }

    private UniversityClass createNewUniversityClass(Consumer<UniversityClass> customizationAction) {

        UniversityClass universityClass = new UniversityClass();
        universityClass.setStartDate(LocalDateTime.of(2025, 3, 1, 17, 0, 0));
        universityClass.setEndDate(LocalDateTime.of(2025, 1, 10, 15, 10, 0));
        universityClass.setSubject(sampleSubject);
        customizationAction.accept(universityClass);
        return universityClass;
    }

    @Test
    public void testSave() {
        createSampleUniversityClass(universityClass -> {});
        final UniversityClass result = assertAndGetExactlyOneRecord();

        SoftAssertions.assertSoftly(assertion -> {
            assertion.assertThat(result.getId()).isGreaterThan(0);
            assertion.assertThat(result.getStartDate()).isEqualTo(LocalDateTime.of(2025, 3, 1, 17, 0, 0));
            assertion.assertThat(result.getEndDate()).isEqualTo(LocalDateTime.of(2025, 1, 10, 15, 10, 0));
            assertion.assertThat(result.getSubject())
                    .isNotNull().extracting(Subject::getTitle).isEqualTo("Algorytmy i struktury danych");});

    }

    @Test
    public void testSaveEntityExistsException() {
        createSampleUniversityClass(universityClass -> {});
        Assert.assertThrows(EntityExistsException.class, ()-> createSampleUniversityClass(universityClass -> universityClass.setId(1)));
    }


    @Test
    public void update() {
        createSampleUniversityClass(s -> s.setEndDate(LocalDateTime.of(2023, 1, 10, 15, 10, 0)));

        {
            final UniversityClass existingUniversityClass = assertAndGetExactlyOneRecord();
            existingUniversityClass.setEndDate(LocalDateTime.of(2025, 1, 10, 15, 10, 0));
            universityClassDao.update(existingUniversityClass);
        }

        final UniversityClass result = assertAndGetExactlyOneRecord();
        Assert.assertEquals(LocalDateTime.of(2025, 1, 10, 15, 10, 0), result.getEndDate());

    }

    @Test
    public void delete() {
        createSampleUniversityClass(s -> {
        });

        final UniversityClass existingUniversityClass = assertAndGetExactlyOneRecord();
        universityClassDao.delete(existingUniversityClass);

        assertExactlyNoRecords();

    }

    @Test
    public void deleteAll() {
        createSampleUniversityClass(s -> {
        });

        universityClassDao.deleteAll();

        assertExactlyNoRecords();

    }

    @Test
    public void findById() {
        final Integer universityClassId = createSampleUniversityClass(s -> {
        }).getId();

        final var result = universityClassDao.findById(universityClassId);

        Assert.assertEquals(LocalDateTime.of(2025, 3, 1, 17, 0, 0), result.getStartDate());
    }

    @Test
    public void testFindAllWhenEmpty() {
        assertExactlyNoRecords();
    }

    public void testFindAllWithOne() {
        createSampleUniversityClass(s -> {
        });

        final UniversityClass result = assertAndGetExactlyOneRecord();

        Assert.assertEquals(LocalDateTime.of(2025, 3, 1, 17, 0, 0), result.getStartDate());
    }


    @Test
    public void findBySubject() {

       Subject differentSubject =  createSampleSubject(s -> {
            s.setTitle("Programowanie Java");
            s.setSemester("IV");
        });

        createSampleUniversityClass(universityClass -> {});

        createSampleUniversityClass(universityClass -> {universityClass.setSubject(differentSubject);});

        final List<UniversityClass> result = universityClassDao.findBySubject(sampleSubject);

        Assert.assertEquals(1, result.size());

    }
}