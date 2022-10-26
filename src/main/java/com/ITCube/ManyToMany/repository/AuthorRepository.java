package com.ITCube.ManyToMany.repository;

import com.ITCube.ManyToMany.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author,Long> {

    Iterable<Author> findByFirstNameIgnoreCase(String name);

}
