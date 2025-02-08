package pl.atins.misie.sos.dao;

import pl.atins.misie.sos.model.Address;

import java.util.List;
public interface AddressDao {

    List<Address> findAll();

    void save(Address address);

    void delete(Address address);

    void update(Address address);

    void deleteAll();

    Address findByAdressLines(String addressLine1, String addressLine2);
}
