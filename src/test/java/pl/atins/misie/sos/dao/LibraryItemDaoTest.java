package pl.atins.misie.sos.dao;

import jakarta.transaction.Transactional;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.atins.misie.sos.model.LibraryItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@Transactional
@Rollback(true)
public class LibraryItemDaoTest {

    @Autowired
    LibraryItemDao libraryItemDao;

    @Test
    public void testFindAll() {
        Assert.assertEquals(0, libraryItemDao.findAll().size());

        LibraryItem item = new LibraryItem();
        item.setQuantity(10);
        libraryItemDao.save(item);

        Assert.assertEquals(1, libraryItemDao.findAll().size());

        LibraryItem item2 = new LibraryItem();
        item2.setQuantity(20);
        libraryItemDao.save(item2);

        Assert.assertEquals(2, libraryItemDao.findAll().size());

        libraryItemDao.deleteAll();
        Assert.assertEquals(0, libraryItemDao.findAll().size());
    }

    @Test
    public void testSave() {
        LibraryItem item = new LibraryItem();
        item.setQuantity(15);
        libraryItemDao.save(item);

        LibraryItem retrievedItem = libraryItemDao.findById(item.getId());
        Assert.assertNotNull(retrievedItem);
        Assert.assertEquals(item.getId(), retrievedItem.getId());
        Assert.assertEquals(item.getQuantity(), retrievedItem.getQuantity());
    }

    @Test
    public void testUpdate() {
        LibraryItem item = new LibraryItem();
        item.setQuantity(10);
        libraryItemDao.save(item);

        item.setQuantity(25);
        libraryItemDao.update(item);

        Assert.assertEquals(Integer.valueOf(25), libraryItemDao.findById(item.getId()).getQuantity());
    }

    @Test
    public void testDelete() {
        LibraryItem item = new LibraryItem();
        item.setQuantity(5);
        libraryItemDao.save(item);

        libraryItemDao.delete(item);

        Assert.assertNull(libraryItemDao.findById(item.getId()));
    }

    @Test
    public void testDeleteAll() {
        LibraryItem item1 = new LibraryItem();
        item1.setQuantity(10);
        libraryItemDao.save(item1);

        LibraryItem item2 = new LibraryItem();
        item2.setQuantity(20);
        libraryItemDao.save(item2);

        libraryItemDao.deleteAll();

        Assert.assertEquals(0, libraryItemDao.findAll().size());
    }
}
