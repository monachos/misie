package pl.atins.misie.sos.dao;


import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.atins.misie.sos.model.Address;
import pl.atins.misie.sos.model.Address;
import pl.atins.misie.sos.model.UniversityClass;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@Transactional
@Rollback(true)
public class AddressDaoTest {

    @Autowired
    AddressDao addressDao;


    @Test
    public void testFindById() {
        final Integer addressId = createSampleAddress(s -> {
        }).getId();

        final var result = addressDao.findById(addressId);

        Assert.assertEquals("Marszalkowska 1", result.getAddressLine1());
    }

    private Address newSampleAddress(Consumer<Address> customizationAction) {
        final var address = new Address();
        address.setAddressLine1("Marszalkowska 1");
        address.setAddressLine2("Warszawa");
        return address;
    }

    private Address createSampleAddress(Consumer<Address> customizationAction) {
        final var address = newSampleAddress(customizationAction);
        return addressDao.save(address);
    }
    
    
    @Test
    public void testFindAll() {


        Assert.assertEquals(0,addressDao.findAll().size());


        Address address = new Address();
        address.setAddressLine1("Marszalkowska 1");
        address.setAddressLine2("Warszawa");
        addressDao.save(address);


        Assert.assertEquals(1,addressDao.findAll().size());

        Address address2 = new Address();
        address2.setAddressLine1("Wygonowa 5");
        address2.setAddressLine2("Opole");
        addressDao.save(address2);

        Assert.assertEquals(2,addressDao.findAll().size());

        addressDao.deleteAll();
        Assert.assertEquals(0,addressDao.findAll().size());

    }

    @Test
    public void testSave() {

        Address address = new Address();
        address.setAddressLine1("Marszalkowska 1");
        address.setAddressLine2("Warszawa");

        addressDao.save(address);

        Assert.assertNotNull(addressDao.findByAdressLines("Marszalkowska 1","Warszawa"));

    }

    @Test
    public void testSaveEntityExistsException() {

        Address address = new Address();
        address.setAddressLine1("Marszalkowska 1");
        address.setAddressLine2("Warszawa");

        addressDao.save(address);

        Address address2 = new Address();

        address2.setId(1);
        address2.setAddressLine1("Marszalkowska 1");
        address2.setAddressLine2("Warszawa");
        Assert.assertThrows(EntityExistsException.class,() -> addressDao.save(address2));

    }
    @Test
    public void testUpdate() {

        Address address = new Address();
        address.setAddressLine1("Marszalkowska 1");
        address.setAddressLine2("Warszawa");
        addressDao.save(address);


        address.setAddressLine2("Wroclaw");

        addressDao.update(address);

        Assert.assertNotNull(addressDao.findByAdressLines("Marszalkowska 1","Wroclaw"));
        Assert.assertNull(addressDao.findByAdressLines("Marszalkowska 1","Warszawa"));
    }


    @Test
    public void testDelete() {

        Address address = new Address();
        address.setAddressLine1("Marszalkowska 1");
        address.setAddressLine2("Warszawa");

        addressDao.save(address);
        addressDao.delete(address);

        Assert.assertNull(addressDao.findByAdressLines("Marszalkowska 1","Warszawa"));
    }

    public void testDeleteIllegalArgumentException() {

        Address address = new Address();
        address.setAddressLine1("Marszalkowska 1");
        address.setAddressLine2("Warszawa");

        addressDao.save(address);

        addressDao.delete(address);

        Assert.assertThrows(IllegalArgumentException.class,() -> addressDao.delete(address));
    }


    @Test
    public void testDeleteAll() {

        Address address = new Address();
        address.setAddressLine1("Marszalkowska 1");
        address.setAddressLine2("Warszawa");

        addressDao.save(address);

        Address address2 = new Address();
        address2.setAddressLine1("Wroclaw");
        address2.setAddressLine2("Wygonowa 5");

        addressDao.save(address2);

        addressDao.deleteAll();

        Assert.assertEquals(0,addressDao.findAll().size());

    }


    @Test
    public void testFindByAdressLines() {

        Address address = new Address();
        address.setAddressLine1("Marszalkowska 1");
        address.setAddressLine2("Warszawa");

        addressDao.save(address);

        Assert.assertNotNull(addressDao.findByAdressLines("Marszalkowska 1","Warszawa"));
        Assert.assertNull(addressDao.findByAdressLines("Wygonowa 5","Wroclaw"));
    }


}
