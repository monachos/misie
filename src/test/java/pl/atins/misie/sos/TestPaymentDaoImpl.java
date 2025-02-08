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
    PaymentDao paymentDao;

    @Test
    public void testFindAll() {
        Assert.assertEquals(0, paymentDao.findAll().size());

        Payment payment = new Payment();
        payment.setStudent(new User());
        payment.setType("Credit");
        payment.setAmount(new BigDecimal("100.00"));
        paymentDao.save(payment);

        Assert.assertEquals(1, paymentDao.findAll().size());

        Payment payment2 = new Payment();
        payment2.setStudent(new User());
        payment2.setType("Debit");
        payment2.setAmount(new BigDecimal("50.00"));
        paymentDao.save(payment2);

        Assert.assertEquals(2, paymentDao.findAll().size());

        paymentDao.deleteAll();
        Assert.assertEquals(0, paymentDao.findAll().size());
    }

    @Test
    public void testSave() {
        Payment payment = new Payment();
        User student = new User();
        student.setId(1);

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
        Assert.assertEquals(1, savedPayment.getStudent().getId().intValue());
    }


    @Test
    public void testUpdate() {
        Payment payment = new Payment();
        payment.setStudent(new User());
        payment.setType("Credit");
        payment.setAmount(new BigDecimal("100.00"));
        paymentDao.save(payment);

        payment.setAmount(new BigDecimal("150.00"));
        paymentDao.update(payment);

        List<Payment> payments = paymentDao.findAll();
        Assert.assertEquals(new BigDecimal("150.00"), payments.get(0).getAmount());
    }

    @Test
    public void testDelete() {
        Payment payment = new Payment();
        payment.setStudent(new User());
        payment.setType("Debit");
        payment.setAmount(new BigDecimal("50.00"));
        paymentDao.save(payment);

        paymentDao.delete(payment);

        Assert.assertTrue(paymentDao.findAll().isEmpty());
    }

    @Test
    public void testDeleteAll() {
        Payment payment1 = new Payment();
        payment1.setStudent(new User());
        payment1.setType("Credit");
        payment1.setAmount(new BigDecimal("300.00"));
        paymentDao.save(payment1);

        Payment payment2 = new Payment();
        payment2.setStudent(new User());
        payment2.setType("Debit");
        payment2.setAmount(new BigDecimal("150.00"));
        paymentDao.save(payment2);

        paymentDao.deleteAll();

        Assert.assertEquals(0, paymentDao.findAll().size());
    }
}
