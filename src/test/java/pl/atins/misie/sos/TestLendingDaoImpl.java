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
        Assert.assertEquals(0, lendingDao.findAll().size());

        User user = new User();
        user.setName("John");
        user.setSurname("Doe");
        LibraryItem item = new LibraryItem();
        item.setQuantity(5);
        Lending lending = new Lending();
        lending.setLendingDate(LocalDateTime.of(2025, 5, 15, 12, 0));
        lending.setUser(user);
        lending.setLibraryItem(item);
        lendingDao.save(lending);

        List<Lending> lendings = lendingDao.findAll();
        Assert.assertEquals(1, lendings.size());
        Lending retrieved = lendings.get(0);
        Assert.assertEquals(LocalDateTime.of(2025, 5, 15, 12, 0), retrieved.getLendingDate());
        Assert.assertEquals("John", retrieved.getUser().getName());
        Assert.assertEquals("Doe", retrieved.getUser().getSurname());
        Assert.assertEquals(Integer.valueOf(5), retrieved.getLibraryItem().getQuantity());
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setName("Alice");
        user.setSurname("Smith");
        LibraryItem item = new LibraryItem();
        item.setQuantity(3);
        Lending lending = new Lending();
        lending.setLendingDate(LocalDateTime.of(2025, 6, 10, 10, 0));
        lending.setUser(user);
        lending.setLibraryItem(item);
        lendingDao.save(lending);

        Lending retrievedLending = lendingDao.findById(lending.getId());
        Assert.assertNotNull(retrievedLending);
        Assert.assertEquals(LocalDateTime.of(2025, 6, 10, 10, 0), retrievedLending.getLendingDate());
        Assert.assertEquals("Alice", retrievedLending.getUser().getName());
        Assert.assertEquals("Smith", retrievedLending.getUser().getSurname());
        Assert.assertEquals(Integer.valueOf(3), retrievedLending.getLibraryItem().getQuantity());
    }

    @Test
    public void testUpdate() {
        User user = new User();
        LibraryItem item = new LibraryItem();
        Lending lending = new Lending();
        lending.setLendingDate(LocalDateTime.of(2025, 7, 20, 15, 0));
        lending.setUser(user);
        lending.setLibraryItem(item);
        lendingDao.save(lending);

        lending.setReturnDate(LocalDateTime.of(2025, 7, 27, 15, 0));
        lendingDao.update(lending);

        Lending updatedLending = lendingDao.findById(lending.getId());
        Assert.assertNotNull(updatedLending.getReturnDate());
        Assert.assertEquals(LocalDateTime.of(2025, 7, 27, 15, 0), updatedLending.getReturnDate());
    }

    @Test
    public void testDelete() {
        User user = new User();
        LibraryItem item = new LibraryItem();
        Lending lending = new Lending();
        lending.setLendingDate(LocalDateTime.of(2025, 8, 5, 9, 0));
        lending.setUser(user);
        lending.setLibraryItem(item);
        lendingDao.save(lending);

        lendingDao.delete(lending);
        Assert.assertNull(lendingDao.findById(lending.getId()));
    }

    @Test
    public void testDeleteAll() {
        User user = new User();
        LibraryItem item = new LibraryItem();
        Lending lending1 = new Lending();
        lending1.setLendingDate(LocalDateTime.of(2025, 9, 1, 14, 0));
        lending1.setUser(user);
        lending1.setLibraryItem(item);
        lendingDao.save(lending1);

        Lending lending2 = new Lending();
        lending2.setLendingDate(LocalDateTime.of(2025, 9, 5, 11, 0));
        lending2.setUser(user);
        lending2.setLibraryItem(item);
        lendingDao.save(lending2);

        lendingDao.deleteAll();
        Assert.assertEquals(0, lendingDao.findAll().size());
    }
}