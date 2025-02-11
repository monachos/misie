package pl.atins.misie.sos.dao;

import pl.atins.misie.sos.model.Address;

import java.util.List;
public interface AddressDao {
    Address findById(int id);
    List<Address> findAll();

    Address save(Address address);

    void delete(Address address);

    void update(Address address);

    void deleteAll();

    Address findByAdressLines(String addressLine1, String addressLine2);
}
