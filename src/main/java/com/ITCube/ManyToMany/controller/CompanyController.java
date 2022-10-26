package com.ITCube.ManyToMany.controller;

import com.ITCube.ManyToMany.exception.AuthorNotFoundException;
import com.ITCube.ManyToMany.exception.CompanyNotFoundException;
import com.ITCube.ManyToMany.model.Company;
import com.ITCube.ManyToMany.service.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/companies")
public class CompanyController {

    private final CompanyServiceImpl company;

    @Autowired
    public CompanyController(CompanyServiceImpl company) {
        this.company = company;
    }

    @ResponseStatus(value= HttpStatus.OK)
    @GetMapping
    public List<Company> findAll(){
        return company.findAll();
    }

    @ResponseStatus(value= HttpStatus.FOUND)
    @GetMapping("/{id}")
    public Company findOne(@PathVariable long id) throws CompanyNotFoundException {
        return company.findOne(id);
    }

    @ResponseStatus(value= HttpStatus.CREATED)
    @PostMapping
    public Company create(@RequestBody Company c){
        return company.create(c);
    }


    @ResponseStatus(value= HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) throws CompanyNotFoundException{
        company.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value= HttpStatus.OK)
    public Company update(@PathVariable long id, @RequestBody Company c) throws CompanyNotFoundException {
        return company.update(id,c);
    }

    @PostMapping("/{idc}/add/{ida}")
    @ResponseStatus(value= HttpStatus.OK)
    public void addAuthor(@PathVariable long idc, @PathVariable long ida) throws AuthorNotFoundException, CompanyNotFoundException {
        company.addAuthor(idc,ida);
    }

    @PostMapping("/{idc}/remove/{ida}")
    @ResponseStatus(value= HttpStatus.OK)
    public void removeAuthor(@PathVariable long idc, @PathVariable long ida) throws AuthorNotFoundException, CompanyNotFoundException {
        company.removeAuthor(idc,ida);
    }


}
