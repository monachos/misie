package pl.atins.misie.sos.dao;

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
import pl.atins.misie.sos.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@Transactional
@Rollback(true)
public class SubjectDaoTest {
    @Autowired
    SubjectDao subjectDao;

    @Autowired
    AddressDao addressDao;

    Address warsawAddress;

    @Autowired
    UserDao userDao;

    private User johnLisp;

    @Autowired
    DepartmentDao departmentDao;

    private Department mainDepartment;

    private Subject assertAndGetExactlyOneRecord() {
        final List<Subject> result = subjectDao.findAll();
        Assert.assertEquals(1, result.size());
        return result.get(0);
    }

    private void assertExactlyNoRecords() {
        final List<Subject> result = subjectDao.findAll();
        Assert.assertTrue(result.isEmpty());
    }

    private Subject createSampleSubject(Consumer<Subject> customizationAction) {
        final var subject = SampleData.newSubject(s -> {
            s.setDepartment(mainDepartment);
            customizationAction.accept(s);
        });
        return subjectDao.save(subject);
    }

    @Before
    public void setUp() {
        warsawAddress = new Address();
        warsawAddress.setAddressLine1("Marszalkowska 1");
        warsawAddress.setAddressLine2("Warszawa");
        addressDao.save(warsawAddress);

        johnLisp = new User();
        johnLisp.setStudentIdNumber("W%s".formatted(UUID.randomUUID()));
        johnLisp.setActive(true);
        johnLisp.setDeleted(false);
        johnLisp.setEmail("%s@example.com".formatted(UUID.randomUUID()));
        johnLisp.setPassword("qwerty");
        johnLisp.setPhone2fa("123456789");
        johnLisp.setAcceptedPrivacyPolicy(true);
        johnLisp.setAcceptedTermsOfUse(true);
        johnLisp.setName("John");
        johnLisp.setSurname("Lisp");
        johnLisp.setRegisteredAddress(warsawAddress);
        johnLisp.setBlockedAccount(false);
        userDao.save(johnLisp);

        mainDepartment = new Department();
        mainDepartment.setName("Main");
        departmentDao.save(mainDepartment);
    }

    @Test
    public void testSave() {
        createSampleSubject(s -> s.setLecturer(johnLisp));

        final Subject result = assertAndGetExactlyOneRecord();

        SoftAssertions.assertSoftly(assertion -> {
            assertion.assertThat(result.getId()).isGreaterThan(0);
            assertion.assertThat(result.getTime()).isEqualTo(LocalDateTime.of(2025, 1, 10, 15, 10, 0));
            assertion.assertThat(result.getDescription()).isEqualTo("Sample description");
            assertion.assertThat(result.getTitle()).isEqualTo("Algorytmy i struktury danych");
            assertion.assertThat(result.getMaxStudents()).isEqualTo(12);
            assertion.assertThat(result.getIsActive()).isTrue();
            assertion.assertThat(result.getSemester()).isEqualTo("VI");
            assertion.assertThat(result.getAcademicYear()).isEqualTo(2025);
            assertion.assertThat(result.getRegistrationStart()).isEqualTo(LocalDateTime.of(2025, 3, 1, 9, 0, 0));
            assertion.assertThat(result.getRegistrationEnd()).isEqualTo(LocalDateTime.of(2025, 3, 5, 17, 0, 0));
            assertion.assertThat(result.getRoomNumber()).isEqualTo("3.11");
            assertion.assertThat(result.getLecturer())
                    .isNotNull()
                    .extracting(User::getSurname)
                    .isEqualTo("Lisp");
            assertion.assertThat(result.getDepartment())
                    .isNotNull()
                    .extracting(Department::getName)
                    .isEqualTo("Main");
        });
    }

    @Test
    public void testUpdate() {
        createSampleSubject(s -> s.setRoomNumber("3.10"));

        {
            final Subject existingSubject = assertAndGetExactlyOneRecord();
            existingSubject.setRoomNumber("2.1");
            subjectDao.update(existingSubject);
        }

        final Subject result = assertAndGetExactlyOneRecord();
        Assert.assertEquals("2.1", result.getRoomNumber());
    }

    @Test
    public void testDelete() {
        createSampleSubject(s -> {
        });

        final Subject existingSubject = assertAndGetExactlyOneRecord();
        subjectDao.delete(existingSubject);

        assertExactlyNoRecords();
    }

    @Test
    public void testDeleteAll() {
        createSampleSubject(s -> {
        });

        subjectDao.deleteAll();

        assertExactlyNoRecords();
    }

    @Test
    public void testFindById() {
        final Integer subjectId = createSampleSubject(s -> {
        }).getId();

        final var result = subjectDao.findById(subjectId);

        Assert.assertEquals("Algorytmy i struktury danych", result.getTitle());
    }

    @Test
    public void testFindAllWhenEmpty() {
        assertExactlyNoRecords();
    }

    @Test
    public void testFindAllWithOne() {
        createSampleSubject(s -> {
        });

        final Subject result = assertAndGetExactlyOneRecord();

        Assert.assertEquals("Algorytmy i struktury danych", result.getTitle());
    }

    @Test
    public void testFindBySemester() {
        createSampleSubject(s -> {
            s.setTitle("Algorytmy i struktury danych");
            s.setSemester("III");
        });
        createSampleSubject(s -> {
            s.setTitle("Programowanie funkcyjne");
            s.setSemester("III");
        });
        createSampleSubject(s -> {
            s.setTitle("Programowanie Java");
            s.setSemester("IV");
        });

        final List<Subject> result = subjectDao.findBySemester("III");

        Assert.assertEquals(2, result.size());
        Assert.assertEquals(1, result.stream()
                .filter(s -> "Algorytmy i struktury danych".equals(s.getTitle()))
                .count());
        Assert.assertEquals(1, result.stream()
                .filter(s -> "Programowanie funkcyjne".equals(s.getTitle()))
                .count());
    }

    @Test
    public void testFindByAcademicYear() {
        createSampleSubject(s -> {
            s.setTitle("Algorytmy i struktury danych");
            s.setAcademicYear(2010);
        });
        createSampleSubject(s -> {
            s.setTitle("Programowanie funkcyjne");
            s.setAcademicYear(2015);
        });
        createSampleSubject(s -> {
            s.setTitle("Programowanie Java");
            s.setAcademicYear(2025);
        });

        final List<Subject> result = subjectDao.findByAcademicYear(2010);

        Assert.assertEquals(1, result.size());
        Assert.assertEquals("Algorytmy i struktury danych", result.get(0).getTitle());
    }

    @Test
    public void testFindActiveSubjects() {
        createSampleSubject(s -> {
            s.setTitle("Algorytmy i struktury danych");
            s.setIsActive(true);
        });
        createSampleSubject(s -> {
            s.setTitle("Programowanie funkcyjne");
            s.setIsActive(false);
        });

        final List<Subject> result = subjectDao.findActiveSubjects();

        Assert.assertEquals(1, result.size());
        Assert.assertEquals("Algorytmy i struktury danych", result.get(0).getTitle());
    }

    @Test
    public void testFindByLecturerId() {
        createSampleSubject(s -> {
            s.setTitle("Algorytmy i struktury danych");
            s.setLecturer(johnLisp);
        });
        createSampleSubject(s -> {
            s.setTitle("Programowanie funkcyjne");
            s.setLecturer(johnLisp);
        });
        createSampleSubject(s -> {
            s.setTitle("Programowanie Java");
            s.setLecturer(null);
        });

        final List<Subject> result = subjectDao.findByLecturerId(johnLisp.getId());

        Assert.assertEquals(2, result.size());
        Assert.assertEquals(1, result.stream()
                .filter(s -> "Algorytmy i struktury danych".equals(s.getTitle()))
                .count());
        Assert.assertEquals(1, result.stream()
                .filter(s -> "Programowanie funkcyjne".equals(s.getTitle()))
                .count());
    }
}
