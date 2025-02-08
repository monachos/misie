package pl.atins.misie.sos.dao;

import pl.atins.misie.sos.model.Address;

import java.util.List;
public interface AddressDao {

//    public Address findById(int id);

    public List<Address> findAll();

    public void save(Address address);

    public void delete(Address address);

    public void update(Address address);

    public void deleteAll();

    public Address findByAdressLines(String addressLine1, String addressLine2);
}
