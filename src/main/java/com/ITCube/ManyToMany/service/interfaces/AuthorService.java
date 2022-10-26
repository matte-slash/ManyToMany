package com.ITCube.ManyToMany.service.interfaces;

import com.ITCube.ManyToMany.exception.AuthorNotFoundException;
import com.ITCube.ManyToMany.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> findAll();

    List<Author> findByName(String name);

    Author findOne(long id) throws AuthorNotFoundException;

    Author create(Author a);

    Author update(long id,Author a) throws AuthorNotFoundException;

    void delete(long id) throws AuthorNotFoundException;

}
