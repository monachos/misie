package pl.atins.misie.sos;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.atins.misie.sos.dao.PaymentDao;
import pl.atins.misie.sos.dao.UserDao;
import pl.atins.misie.sos.dao.impl.AddressDaoImpl;
import pl.atins.misie.sos.model.Address;
import pl.atins.misie.sos.model.Payment;
import pl.atins.misie.sos.model.User;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@Transactional
@Rollback(true)
public class TestPaymentDaoImpl {

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private UserDao userDao;
    @Autowired
    private AddressDaoImpl addressDao;

    private User createUser() {
        User student = new User();
        student.setEmail("student@test.com");
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




    @Test
    public void testFindAll() {
        Assert.assertEquals(0, paymentDao.findAll().size());

        User student = createUser();
        Payment payment = new Payment();
        payment.setStudent(student);
        payment.setType("Credit");
        payment.setAmount(new BigDecimal("100.00"));
        paymentDao.save(payment);

        Assert.assertEquals(1, paymentDao.findAll().size());

        Payment payment2 = new Payment();
        payment2.setStudent(student);
        payment2.setType("Debit");
        payment2.setAmount(new BigDecimal("50.00"));
        paymentDao.save(payment2);

        Assert.assertEquals(2, paymentDao.findAll().size());

        paymentDao.deleteAll();
        Assert.assertEquals(0, paymentDao.findAll().size());
    }

    @Test
    public void testSave() {
        User student = createUser();

        Payment payment = new Payment();
        payment.setStudent(student);
        payment.setType("Credit");
        payment.setAmount(new BigDecimal("200.00"));

        paymentDao.save(payment);

        List<Payment> payments = paymentDao.findAll();
        Assert.assertEquals(1, payments.size());

        Payment savedPayment = payments.get(0);

        Assert.assertEquals("Credit", savedPayment.getType());
        Assert.assertEquals(new BigDecimal("200.00"), savedPayment.getAmount());
        Assert.assertNotNull(savedPayment.getStudent());
        Assert.assertEquals(student.getEmail(), savedPayment.getStudent().getEmail());
    }

    @Test
    public void testUpdate() {
        User student = createUser();

        Payment payment = new Payment();
        payment.setStudent(student);
        payment.setType("Credit");
        payment.setAmount(new BigDecimal("100.00"));
        paymentDao.save(payment);

        Payment existingPayment = paymentDao.findAll().get(0);
        existingPayment.setAmount(new BigDecimal("150.00"));
        paymentDao.update(existingPayment);

        Payment updatedPayment = paymentDao.findAll().get(0);
        Assert.assertEquals(new BigDecimal("150.00"), updatedPayment.getAmount());
    }

    @Test
    public void testDelete() {
        User student = createUser();

        Payment payment = new Payment();
        payment.setStudent(student);
        payment.setType("Debit");
        payment.setAmount(new BigDecimal("50.00"));
        paymentDao.save(payment);

        Payment existingPayment = paymentDao.findAll().get(0);
        paymentDao.delete(existingPayment);

        Assert.assertTrue(paymentDao.findAll().isEmpty());
    }

    @Test
    public void testDeleteAll() {
        User student = createUser();

        for (int i = 0; i < 5; i++) {
            Payment payment = new Payment();
            payment.setStudent(student);
            payment.setType("Credit");
            payment.setAmount(new BigDecimal("300.00"));
            paymentDao.save(payment);
        }

        paymentDao.deleteAll();

        Assert.assertEquals(0, paymentDao.findAll().size());
    }
}
