package pl.atins.misie.sos.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import pl.atins.misie.sos.dao.AddressDao;
import pl.atins.misie.sos.model.Address;

import java.util.List;

public class AddressDaoImpl implements AddressDao {
    
    @PersistenceContext
    EntityManager entityManager;


    @Override
    public List<Address> findAll() {
        String hql = "SELECT A FROM Address A";
        TypedQuery<Address> query  = entityManager.createQuery(hql, Address.class);
        return query.getResultList();
    }

    @Override
    public void save(Address address) {
        entityManager.persist(address);
    }

    @Override
    public void delete(Address address) {
        entityManager.remove(address);
    }

    @Override
    public void update(Address address) {
        entityManager.merge(address);
    }

    @Override
    public void deleteAll() {
        String hql = "DELETE FROM Address A";
        Query query  = entityManager.createQuery(hql);
        query.executeUpdate();
    }

    @Override
    public Address findByAdressLines(String adressLine1, String adressLine2) {
        String hql = "SELECT A FROM Address A WHERE A.addressLine1 = :adressLine1 AND A.addressLine2 = :adressLine2";
        TypedQuery<Address> query  = entityManager.createQuery(hql, Address.class);
        query.setParameter("adressLine1", adressLine1);
        query.setParameter("adressLine2", adressLine2);

        try{
            return query.getSingleResult();
        }catch (jakarta.persistence.NoResultException e){
            return null;
        }

    }

}
