package pl.atins.misie.sos.dao;


import pl.atins.misie.sos.model.LibraryItem;

import java.util.List;

public interface LibraryItemDao {


    List<LibraryItem> findAll();

    void save(LibraryItem address);

    void delete(LibraryItem address);

    void update(LibraryItem address);

    void deleteAll();

    public LibraryItem findById(Integer id);
}
