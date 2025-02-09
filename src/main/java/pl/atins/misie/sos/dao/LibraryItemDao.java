package pl.atins.misie.sos.dao;


import pl.atins.misie.sos.model.LibraryItem;

import java.util.List;

public interface LibraryItemDao {


    List<LibraryItem> findAll();

    void save(LibraryItem libraryItem);

    void delete(LibraryItem libraryItem);

    void update(LibraryItem libraryItem);

    void deleteAll();

    public LibraryItem findById(Integer id);
}
