package pl.atins.misie.sos.dao;

import jakarta.transaction.Transactional;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.atins.misie.sos.model.Address;
import pl.atins.misie.sos.model.LibraryItem;
import pl.atins.misie.sos.model.Reservation;
import pl.atins.misie.sos.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@Transactional
@Rollback(true)
public class ReservationDaoTest {

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private UserDao userDao;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private LibraryItemDao libraryItemDao;

    @Before
    public void cleanDatabase() {
        reservationDao.deleteAll();
        libraryItemDao.deleteAll();
        addressDao.deleteAll();
    }

    private User createUser() {
        User student = new User();
        student.setEmail("student" + UUID.randomUUID() + "@test.com");
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

    private LibraryItem createLibraryItem() {
        LibraryItem item = new LibraryItem();
        item.setQuantity(5);
        libraryItemDao.save(item);
        return item;
    }

    private Reservation createReservation() {
        Reservation reservation = new Reservation();
        reservation.setUser(createUser());
        reservation.setLibraryItem(createLibraryItem());
        reservation.setReservationDate(LocalDate.of(2024, 2, 10));
        reservation.setReservationStatus("ACTIVE");
        reservationDao.save(reservation);
        return reservation;
    }

    private void createReservation(Consumer<Reservation> customizationAction) {
        Reservation reservation = new Reservation();
        reservation.setUser(createUser());
        reservation.setLibraryItem(createLibraryItem());
        reservation.setReservationDate(LocalDate.of(2024, 2, 10));
        reservation.setReservationStatus("ACTIVE");
        customizationAction.accept(reservation);
        reservationDao.save(reservation);
    }

    @Test
    public void testFindAll() {
        Assert.assertEquals(0, reservationDao.findAll().size());

        createReservation();
        Assert.assertEquals(1, reservationDao.findAll().size());

        createReservation();
        Assert.assertEquals(2, reservationDao.findAll().size());

        reservationDao.deleteAll();
        Assert.assertEquals(0, reservationDao.findAll().size());
    }

    @Test
    public void testSave() {
        Reservation reservation = createReservation();

        List<Reservation> reservations = reservationDao.findAll();
        Assert.assertEquals(1, reservations.size());

        Reservation savedReservation = reservations.get(0);
        Assert.assertEquals("ACTIVE", savedReservation.getReservationStatus());
        Assert.assertNotNull(savedReservation.getUser());
        Assert.assertNotNull(savedReservation.getLibraryItem());
    }

    @Test
    public void testUpdate() {
        Reservation reservation = createReservation();
        reservation.setReservationStatus("CANCELLED");
        reservationDao.update(reservation);

        Reservation updatedReservation = reservationDao.findById(reservation.getId());
        Assert.assertNotNull(updatedReservation);
        Assert.assertEquals("CANCELLED", updatedReservation.getReservationStatus());
    }

    @Test
    public void testDelete() {
        Reservation reservation = createReservation();
        reservationDao.delete(reservation);
        Assert.assertNull(reservationDao.findById(reservation.getId()));
    }

    @Test
    public void testDeleteAll() {
        for (int i = 0; i < 5; i++) {
            createReservation();
        }
        reservationDao.deleteAll();
        Assert.assertEquals(0, reservationDao.findAll().size());
    }

    @Test
    public void testFindByUserForUnknownData() {
        for (int i = 0; i < 5; i++) {
            createReservation();
        }

        final List<Reservation> result = reservationDao.findByUser(createUser());

        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testFindByUserForExistingUser() {
        final User mainUser = createUser();
        for (int i = 0; i < 5; ++i) {
            createReservation(r -> r.setUser(mainUser));
        }
        final User otherUser = createUser();
        for (int i = 0; i < 3; ++i) {
            createReservation(r -> r.setUser(otherUser));
        }

        final List<Reservation> result = reservationDao.findByUser(mainUser);

        Assert.assertEquals(5, result.size());
    }
}
