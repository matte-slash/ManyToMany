package com.ITCube.ManyToMany.service;

import com.ITCube.ManyToMany.exception.AuthorNotFoundException;
import com.ITCube.ManyToMany.model.Author;
import com.ITCube.ManyToMany.repository.AuthorRepository;
import com.ITCube.ManyToMany.service.interfaces.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository author;

    @Autowired
    public AuthorServiceImpl(AuthorRepository author) {
        this.author = author;
    }

    @Override
    public List<Author> findAll() {
        List<Author> result = new ArrayList<Author>();
        author.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Author findOne(long id) throws AuthorNotFoundException {
        return author.findById(id).orElseThrow(()-> new AuthorNotFoundException("Author "+id+" not found"));
    }

    @Override
    public Author create(Author a) {
        return author.save(a);
    }

    @Override
    public Author update(long id, Author a) throws AuthorNotFoundException {
        Author result=findOne(id);
        result.setFirstName(a.getFirstName());
        result.setLastName(a.getLastName());
        return author.save(result);
    }

    @Override
    public void delete(long id) throws AuthorNotFoundException {
        findOne(id);
        author.deleteById(id);
    }
}
