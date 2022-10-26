package com.ITCube.ManyToMany.controller;

import com.ITCube.ManyToMany.exception.AuthorNotFoundException;
import com.ITCube.ManyToMany.model.Author;
import com.ITCube.ManyToMany.service.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/authors")
public class AuthorController {

    private final AuthorServiceImpl author;

    @Autowired
    public AuthorController(AuthorServiceImpl author) {
        this.author = author;
    }

    @GetMapping
    @ResponseStatus(value= HttpStatus.OK)
    public List<Author> findAll(@RequestParam(name="name",required = false) String name){

        if(name!=null){
            return author.findByName(name);
        }else{
            return author.findAll();
        }


    }

    @GetMapping("/{id}")
    @ResponseStatus(value=HttpStatus.OK)
    public Author findOne(@PathVariable long id) throws AuthorNotFoundException {
        return author.findOne(id);
    }

    @PostMapping()
    @ResponseStatus(value=HttpStatus.CREATED)
    public Author create(@RequestBody Author a){

        return author.create(a);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value=HttpStatus.OK)
    public Author update(@PathVariable long id, @RequestBody Author a) throws AuthorNotFoundException {

        return author.update(id,a);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) throws AuthorNotFoundException {
        author.delete(id);
    }


}
