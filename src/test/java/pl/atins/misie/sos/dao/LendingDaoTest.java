package pl.atins.misie.sos.dao;

import jakarta.transaction.Transactional;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.atins.misie.sos.dao.impl.AddressDaoImpl;
import pl.atins.misie.sos.model.Address;
import pl.atins.misie.sos.model.Lending;
import pl.atins.misie.sos.model.LibraryItem;
import pl.atins.misie.sos.model.User;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml"})
@Transactional
@Rollback(true)
public class LendingDaoTest {

    @Autowired
    LendingDao lendingDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LibraryItemDao libraryItemDao;

    @Autowired
    private AddressDaoImpl addressDao;

    private User createUser() {
        Address address = new Address();
        address.setAddressLine1("Test Street 123");
        addressDao.save(address);

        User student = SampleData.newUser(u -> u.setRegisteredAddress(address));
        userDao.save(student);

        return student;
    }

    private LibraryItem createAndSaveLibraryItem() {
        LibraryItem item = new LibraryItem();
        item.setQuantity(5);
        libraryItemDao.save(item);
        return item;
    }

    @Test
    public void testFindAll() {
        Assert.assertEquals(0, lendingDao.findAll().size());

        User user = createUser();
        LibraryItem item = createAndSaveLibraryItem();

        Lending lending = new Lending();
        lending.setLendingDate(LocalDateTime.of(2025, 5, 15, 12, 0));
        lending.setUser(user);
        lending.setLibraryItem(item);
        lendingDao.save(lending);

        List<Lending> lendings = lendingDao.findAll();
        Assert.assertEquals(1, lendings.size());
        Lending retrieved = lendings.get(0);
        Assert.assertEquals(LocalDateTime.of(2025, 5, 15, 12, 0), retrieved.getLendingDate());
        Assert.assertEquals("Jan", retrieved.getUser().getName());
        Assert.assertEquals("Kowalski", retrieved.getUser().getSurname());
        Assert.assertEquals(Integer.valueOf(5), retrieved.getLibraryItem().getQuantity());
    }

    @Test
    public void testSave() {
        User user = createUser();
        LibraryItem item = createAndSaveLibraryItem();

        Lending lending = new Lending();
        lending.setLendingDate(LocalDateTime.of(2025, 6, 10, 10, 0));
        lending.setUser(user);
        lending.setLibraryItem(item);
        lendingDao.save(lending);

        Lending retrievedLending = lendingDao.findById(lending.getId());
        Assert.assertNotNull(retrievedLending);
        Assert.assertEquals(LocalDateTime.of(2025, 6, 10, 10, 0), retrievedLending.getLendingDate());
        Assert.assertEquals("Jan", retrievedLending.getUser().getName());
        Assert.assertEquals("Kowalski", retrievedLending.getUser().getSurname());
        Assert.assertEquals(Integer.valueOf(5), retrievedLending.getLibraryItem().getQuantity());
    }

    @Test
    public void testUpdate() {
        User user = createUser();
        LibraryItem item = createAndSaveLibraryItem();

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
        User user = createUser();
        LibraryItem item = createAndSaveLibraryItem();

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
        User user = createUser();
        LibraryItem item = createAndSaveLibraryItem();

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

    @Test
    public void testGetCountByUser() {
        final User mainUser = createUser();
        for (int i=0; i<3; ++i) {
            final var lending = new Lending();
            lending.setLendingDate(LocalDateTime.of(2025, 9, 1, 14, 0));
            lending.setUser(mainUser);
            lending.setLibraryItem(createAndSaveLibraryItem());
            lendingDao.save(lending);
        }
        final User otherUser = createUser();
        for (int i=0; i<6; ++i) {
            final var lending = new Lending();
            lending.setLendingDate(LocalDateTime.of(2025, 9, 1, 14, 0));
            lending.setUser(otherUser);
            lending.setLibraryItem(createAndSaveLibraryItem());
            lendingDao.save(lending);
        }

        long result = lendingDao.getCountByUser(mainUser);

        Assert.assertEquals(3, result);
    }
}
