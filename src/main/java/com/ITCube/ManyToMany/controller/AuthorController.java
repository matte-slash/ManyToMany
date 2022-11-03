package com.ITCube.ManyToMany.controller;

import com.ITCube.ManyToMany.dto.AuthorDTO;
import com.ITCube.ManyToMany.exception.AuthorNotFoundException;
import com.ITCube.ManyToMany.service.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path="/authors")
@Validated
public class AuthorController {

    private final AuthorServiceImpl author;

    @Autowired
    public AuthorController(AuthorServiceImpl author) {
        this.author = author;
    }

    @GetMapping
    @ResponseStatus(value= HttpStatus.OK)
    public List<AuthorDTO> findAll(@RequestParam(name="name",required = false) String name){
        if(name!=null){
            return author.findAuthorByName(name);
        }else{
            return author.findAllAuthor();
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(value=HttpStatus.OK)
    public AuthorDTO findOneAuthor(@PathVariable long id) {
        return author.findOneAuthor(id);
    }

    @PostMapping()
    @ResponseStatus(value=HttpStatus.CREATED)
    public AuthorDTO create(@Valid @RequestBody AuthorDTO a){

        return author.createAuthor(a);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value=HttpStatus.OK)
    public AuthorDTO updateAuthor(@PathVariable long id,@Valid @RequestBody AuthorDTO a) {

        return author.updateAuthor(id,a);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable long id) throws AuthorNotFoundException {
        author.deleteAuthor(id);
    }


}
