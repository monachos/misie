package pl.atins.misie.sos.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import pl.atins.misie.sos.dao.LibraryItemDao;
import pl.atins.misie.sos.model.LibraryItem;

import java.util.List;

public class LibraryItemDaoImpl implements LibraryItemDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<LibraryItem> findAll() {
        String hql = "SELECT L FROM LibraryItem L";
        TypedQuery<LibraryItem> query  = entityManager.createQuery(hql, LibraryItem.class);
        return query.getResultList();
    }

    @Override
    public void save(LibraryItem libraryItem) {
        entityManager.persist(libraryItem);
    }

    @Override
    public void delete(LibraryItem libraryItem) {
        entityManager.remove(libraryItem);
    }

    @Override
    public void update(LibraryItem libraryItem) {
        entityManager.merge(libraryItem);
    }

    @Override
    public void deleteAll() {
        String hql = "DELETE FROM LibraryItem";
        Query query  = entityManager.createQuery(hql);
        query.executeUpdate();
    }

    @Override
    public LibraryItem findById(Integer id) {
        return entityManager.find(LibraryItem.class, id);
    }
}
