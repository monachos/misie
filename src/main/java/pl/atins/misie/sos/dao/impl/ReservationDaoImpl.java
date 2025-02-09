package pl.atins.misie.sos.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import pl.atins.misie.sos.dao.ReservationDao;
import pl.atins.misie.sos.model.Reservation;

import java.util.List;

@Repository
@Transactional
public class ReservationDaoImpl implements ReservationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Reservation> findAll() {
        TypedQuery<Reservation> query = entityManager.createQuery("SELECT r FROM Reservation r", Reservation.class);
        return query.getResultList();
    }

    @Override
    public Reservation findById(Integer id) {
        return entityManager.find(Reservation.class, id);
    }

    @Override
    public void save(Reservation reservation) {
        entityManager.persist(reservation);
    }

    @Override
    public void update(Reservation reservation) {
        entityManager.merge(reservation);
    }

    @Override
    public void delete(Reservation reservation) {
        Reservation managedReservation = entityManager.find(Reservation.class, reservation.getId());
        if (managedReservation != null) {
            entityManager.remove(managedReservation);
        }
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Reservation").executeUpdate();
    }
}
