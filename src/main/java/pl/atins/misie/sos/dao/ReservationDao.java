package pl.atins.misie.sos.dao;

import pl.atins.misie.sos.model.Reservation;
import pl.atins.misie.sos.model.User;

import java.util.List;

public interface ReservationDao {
    List<Reservation> findAll();

    Reservation findById(Integer id);

    void save(Reservation reservation);

    void update(Reservation reservation);

    void delete(Reservation reservation);

    void deleteAll();

    List<Reservation> findByUser(User user);
}
