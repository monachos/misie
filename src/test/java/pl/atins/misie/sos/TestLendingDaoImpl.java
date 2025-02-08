package pl.atins.misie.sos;

import jakarta.transaction.Transactional;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.atins.misie.sos.dao.LendingDao;
import pl.atins.misie.sos.model.Lending;
import pl.atins.misie.sos.model.LibraryItem;
import pl.atins.misie.sos.model.User;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@Transactional
@Rollback(true)
public class TestLendingDaoImpl {

    @Autowired
    LendingDao lendingDao;

    @Test
    public void testFindAll() {
        List<Lending> lendings = lendingDao.findAll();
        Assert.assertEquals(0, lendings.size());

        Lending lending = new Lending();
        lending.setLendingDate(LocalDateTime.now());
        lending.setUser(new User());
        lending.setLibraryItem(new LibraryItem());
        lendingDao.save(lending);

        Assert.assertEquals(1, lendingDao.findAll().size());
    }

    @Test
    public void testSave() {
        Lending lending = new Lending();
        lending.setLendingDate(LocalDateTime.now());
        lending.setUser(new User());
        lending.setLibraryItem(new LibraryItem());
        lendingDao.save(lending);

        Assert.assertNotNull(lendingDao.findById(lending.getId()));
    }

    @Test
    public void testUpdate() {
        Lending lending = new Lending();
        lending.setLendingDate(LocalDateTime.now());
        lending.setUser(new User());
        lending.setLibraryItem(new LibraryItem());
        lendingDao.save(lending);

        lending.setReturnDate(LocalDateTime.now().plusDays(7));
        lendingDao.update(lending);

        Assert.assertNotNull(lendingDao.findById(lending.getId()).getReturnDate());
    }

    @Test
    public void testDelete() {
        Lending lending = new Lending();
        lending.setLendingDate(LocalDateTime.now());
        lending.setUser(new User());
        lending.setLibraryItem(new LibraryItem());
        lendingDao.save(lending);

        lendingDao.delete(lending);

        Assert.assertNull(lendingDao.findById(lending.getId()));
    }

    @Test
    public void testDeleteAll() {
        Lending lending1 = new Lending();
        lending1.setLendingDate(LocalDateTime.now());
        lending1.setUser(new User());
        lending1.setLibraryItem(new LibraryItem());
        lendingDao.save(lending1);

        Lending lending2 = new Lending();
        lending2.setLendingDate(LocalDateTime.now());
        lending2.setUser(new User());
        lending2.setLibraryItem(new LibraryItem());
        lendingDao.save(lending2);

        lendingDao.deleteAll();

        Assert.assertEquals(0, lendingDao.findAll().size());
    }
}
