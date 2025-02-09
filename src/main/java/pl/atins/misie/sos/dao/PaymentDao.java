package pl.atins.misie.sos.dao;

import pl.atins.misie.sos.model.Payment;

import java.util.List;

public interface PaymentDao {

    List<Payment> findAll();

    void save(Payment payment);

    void delete(Payment payment);

    void update(Payment payment);

    void deleteAll();
}
