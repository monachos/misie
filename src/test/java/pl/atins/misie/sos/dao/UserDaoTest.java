package pl.atins.misie.sos.dao;

import org.assertj.core.api.SoftAssertions;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.atins.misie.sos.model.Address;
import pl.atins.misie.sos.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@Transactional
@Rollback(true)
public class UserDaoTest {
    @Autowired
    UserDao userDao;

    @Autowired
    AddressDao addressDao;

    Address warsawAddress;
    Address wroclawAddress;
    Address poznanAddress;


    private User assertAndGetExactlyOneRecord() {
        final List<User> result = userDao.findAll();
        Assert.assertEquals(1, result.size());
        return result.get(0);
    }

    private void assertExactlyNoRecords() {
        Assert.assertEquals(0, userDao.count());
    }

    @Before
    public void setUp() {
        warsawAddress = new Address();
        warsawAddress.setAddressLine1("Marszalkowska 1");
        warsawAddress.setAddressLine2("Warszawa");
        addressDao.save(warsawAddress);

        wroclawAddress = new Address();
        wroclawAddress.setAddressLine1("Wejherowska 1");
        wroclawAddress.setAddressLine2("Wroclaw");
        addressDao.save(wroclawAddress);

        poznanAddress = new Address();
        poznanAddress.setAddressLine1("Sw. Marcina 1");
        poznanAddress.setAddressLine2("Poznan");
        addressDao.save(poznanAddress);
    }

    @Test
    public void testFindAllWhenEmpty() {
        assertExactlyNoRecords();
    }

    private User newSampleUser(Consumer<User> customizationAction) {
        final User user = new User();
        user.setStudentIdNumber("%s".formatted(UUID.randomUUID()));
        user.setActive(true);
        user.setDeleted(false);
        user.setEmail("%s@example.com".formatted(UUID.randomUUID()));
        user.setPassword("qwerty");
        user.setPhone2fa("123456789");
        user.setAcceptedPrivacyPolicy(true);
        user.setAcceptedTermsOfUse(true);
        user.setName("Jan");
        user.setSurname("Kowalski");
        user.setRegisteredAddress(warsawAddress);
        user.setBlockedAccount(false);
        customizationAction.accept(user);
        return user;
    }

    private User saveSampleUser(Consumer<User> customizationAction) {
        final User user = newSampleUser(customizationAction);
        return userDao.save(user);
    }

    @Test
    public void testFindById() {
        final Integer userId = saveSampleUser(u -> {}).getId();

        final Optional<User> result = userDao.findById(userId);
        Assert.assertTrue(result.isPresent());
        final User user = result.get();

        Assert.assertEquals("Kowalski", user.getSurname());
    }

    @Test
    public void testFindByEmail() {
        final String email = saveSampleUser(u -> {}).getEmail();

        final Optional<User> result = userDao.findByEmail(email);
        Assert.assertTrue(result.isPresent());
        final User user = result.get();

        Assert.assertEquals("Kowalski", user.getSurname());
    }

    @Test
    public void testSave() {
        saveSampleUser(u -> {
            u.setStudentIdNumber("1111");
            u.setEmail("1111@example.com");
            u.setRegisteredAddress(warsawAddress);
            u.setResidentialAddress(wroclawAddress);
            u.setCorrespondenceAddress(poznanAddress);
        });

        final User result = assertAndGetExactlyOneRecord();

        SoftAssertions.assertSoftly(assertion -> {
            assertion.assertThat(result.getStudentIdNumber()).isEqualTo("1111");
            assertion.assertThat(result.getActive()).isTrue();
            assertion.assertThat(result.getDeleted()).isFalse();
            assertion.assertThat(result.getEmail()).isEqualTo("1111@example.com");
            assertion.assertThat(result.getPassword()).isEqualTo("qwerty");
            assertion.assertThat(result.getPhone2fa()).isEqualTo("123456789");
            assertion.assertThat(result.getAcceptedPrivacyPolicy()).isTrue();
            assertion.assertThat(result.getAcceptedTermsOfUse()).isTrue();
            assertion.assertThat(result.getName()).isEqualTo("Jan");
            assertion.assertThat(result.getSurname()).isEqualTo("Kowalski");
            assertion.assertThat(result.getRegisteredAddress().getAddressLine2()).isEqualTo("Warszawa");
            assertion.assertThat(result.getResidentialAddress().getAddressLine2()).isEqualTo("Wroclaw");
            assertion.assertThat(result.getCorrespondenceAddress().getAddressLine2()).isEqualTo("Poznan");
            assertion.assertThat(result.getBlockedAccount()).isFalse();
        });
    }

    @Test
    public void testSaveNonUniqueStudentNumber() {
        saveSampleUser(u -> {
            u.setStudentIdNumber("1111");
            u.setRegisteredAddress(warsawAddress);
        });
        var newUser = newSampleUser(u -> {
            u.setStudentIdNumber("1111");
            u.setRegisteredAddress(wroclawAddress);
        });
        Assert.assertThrows(ConstraintViolationException.class, () -> userDao.save(newUser));
    }

    @Test
    public void testSaveNonUniqueEmail() {
        saveSampleUser(u -> {
            u.setEmail("00@example.com");
            u.setRegisteredAddress(warsawAddress);
        });
        var newUser = newSampleUser(u -> {
            u.setEmail("00@example.com");
            u.setRegisteredAddress(wroclawAddress);
        });
        Assert.assertThrows(ConstraintViolationException.class, () -> userDao.save(newUser));
    }

    @Test
    public void testExistsById() {
        var userId = saveSampleUser(u -> {}).getId();

        final boolean result = userDao.existsById(userId);

        Assert.assertTrue(result);
    }

    @Test
    public void testCount() {
        saveSampleUser(u -> {
            u.setRegisteredAddress(warsawAddress);
        });
        saveSampleUser(u -> {
            u.setRegisteredAddress(wroclawAddress);
        });

        final long result = userDao.count();

        Assert.assertEquals(2, result);
    }

    @Test
    public void testDeleteById() {
        Integer userId = saveSampleUser(u -> {}).getId();

        userDao.deleteById(userId);

        assertExactlyNoRecords();
    }

    @Test
    public void testDelete() {
        saveSampleUser(u -> {});

        final User existingUser = assertAndGetExactlyOneRecord();
        userDao.delete(existingUser);

        assertExactlyNoRecords();
    }
}
