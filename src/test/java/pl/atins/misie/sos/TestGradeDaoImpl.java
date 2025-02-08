package pl.atins.misie.sos;

import jakarta.transaction.Transactional;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.atins.misie.sos.dao.GradeDao;
import pl.atins.misie.sos.model.Grade;
import pl.atins.misie.sos.model.StudentSubject;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@Transactional
@Rollback(true)
public class TestGradeDaoImpl {

    @Autowired
    GradeDao gradeDao;

    @Test
    public void testFindAll() {
        List<Grade> grades = gradeDao.findAll();
        Assert.assertEquals(0, grades.size());

        Grade grade = new Grade();
        grade.setStudentSubject(new StudentSubject());
        grade.setGrade(BigDecimal.valueOf(4.5));
        grade.setType("Exam");
        grade.setGradingDate(LocalDateTime.now());
        gradeDao.save(grade);

        Assert.assertEquals(1, gradeDao.findAll().size());
    }

    @Test
    public void testSave() {
        Grade grade = new Grade();
        grade.setStudentSubject(new StudentSubject());
        grade.setGrade(BigDecimal.valueOf(5.0));
        grade.setType("Final");
        grade.setGradingDate(LocalDateTime.now());
        gradeDao.save(grade);

        Assert.assertNotNull(gradeDao.findById(grade.getId()));
    }

    @Test
    public void testUpdate() {
        Grade grade = new Grade();
        grade.setStudentSubject(new StudentSubject());
        grade.setGrade(BigDecimal.valueOf(3.0));
        grade.setType("Midterm");
        grade.setGradingDate(LocalDateTime.now());
        gradeDao.save(grade);

        grade.setGrade(BigDecimal.valueOf(4.0));
        gradeDao.update(grade);

        Assert.assertEquals(BigDecimal.valueOf(4.0), gradeDao.findById(grade.getId()).getGrade());
    }

    @Test
    public void testDelete() {
        Grade grade = new Grade();
        grade.setStudentSubject(new StudentSubject());
        grade.setGrade(BigDecimal.valueOf(2.0));
        grade.setType("Quiz");
        grade.setGradingDate(LocalDateTime.now());
        gradeDao.save(grade);

        gradeDao.delete(grade);

        Assert.assertNull(gradeDao.findById(grade.getId()));
    }

    @Test
    public void testDeleteAll() {
        Grade grade1 = new Grade();
        grade1.setStudentSubject(new StudentSubject());
        grade1.setGrade(BigDecimal.valueOf(3.5));
        grade1.setType("Test");
        grade1.setGradingDate(LocalDateTime.now());
        gradeDao.save(grade1);

        Grade grade2 = new Grade();
        grade2.setStudentSubject(new StudentSubject());
        grade2.setGrade(BigDecimal.valueOf(4.0));
        grade2.setType("Final");
        grade2.setGradingDate(LocalDateTime.now());
        gradeDao.save(grade2);

        gradeDao.deleteAll();

        Assert.assertEquals(0, gradeDao.findAll().size());
    }
}
