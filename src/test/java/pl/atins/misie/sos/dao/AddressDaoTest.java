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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@Transactional
@Rollback(true)
public class AddressDaoTest {


    @Autowired
    AddressDao addressDao;


    @Test
    public void testFindAll() {


        Assert.assertEquals(0,addressDao.findAll().size());


        Address address = new Address();
        address.setAddressLine1("sss");
        address.setAddressLine2("aaa");
        addressDao.save(address);


        Assert.assertEquals(1,addressDao.findAll().size());

        Address address2 = new Address();
        address2.setAddressLine1("111");
        address2.setAddressLine2("222");
        addressDao.save(address2);

        Assert.assertEquals(2,addressDao.findAll().size());

        addressDao.deleteAll();
        Assert.assertEquals(0,addressDao.findAll().size());

    }

    @Test
    public void testSave() {

        Address address = new Address();
        address.setAddressLine1("sss");
        address.setAddressLine2("aaa");

        addressDao.save(address);

        Assert.assertNotNull(addressDao.findByAdressLines("sss","aaa"));

        Address address2 = new Address();

        address2.setId(1);
        address2.setAddressLine1("sss");
        address2.setAddressLine2("aaa");
        Assert.assertThrows(EntityExistsException.class,() -> addressDao.save(address2));

    }

    @Test
    public void testUpdate() {

        Address address = new Address();
        address.setAddressLine1("sss");
        address.setAddressLine2("aaa");
        addressDao.save(address);


        address.setAddressLine2("bbb");

        addressDao.update(address);

        Assert.assertNotNull(addressDao.findByAdressLines("sss","bbb"));
        Assert.assertNull(addressDao.findByAdressLines("sss","aaa"));

    }


    @Test
    public void testDelete() {

        Address address = new Address();
        address.setAddressLine1("sss");
        address.setAddressLine2("aaa");

        addressDao.save(address);

        addressDao.delete(address);

        Assert.assertNull(addressDao.findByAdressLines("sss","aaa"));

        Assert.assertThrows(IllegalArgumentException.class,() -> addressDao.delete(address));
    }


    @Test
    public void testDeleteAll() {

        Address address = new Address();
        address.setAddressLine1("sss");
        address.setAddressLine2("aaa");

        addressDao.save(address);

        Address address2 = new Address();
        address2.setAddressLine1("bbb");
        address2.setAddressLine2("111");

        addressDao.save(address2);

        addressDao.deleteAll();

        Assert.assertEquals(0,addressDao.findAll().size());

    }


    @Test
    public void testFindByAdressLines() {

        Address address = new Address();
        address.setAddressLine1("sss");
        address.setAddressLine2("aaa");

        addressDao.save(address);

        Assert.assertNotNull(addressDao.findByAdressLines("sss","aaa"));
        Assert.assertNull(addressDao.findByAdressLines("111","bbb"));


    }


}
