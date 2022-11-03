package com.ITCube.ManyToMany.controller;

import com.ITCube.ManyToMany.dto.CompanyDTO;
import com.ITCube.ManyToMany.exception.AuthorNotFoundException;
import com.ITCube.ManyToMany.exception.CompanyNotFoundException;
import com.ITCube.ManyToMany.model.Company;
import com.ITCube.ManyToMany.service.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path="/companies")
@Validated
public class CompanyController {

    private final CompanyServiceImpl company;

    @Autowired
    public CompanyController(CompanyServiceImpl company) {
        this.company = company;
    }

    @ResponseStatus(value= HttpStatus.OK)
    @GetMapping
    public List<CompanyDTO> find(@RequestParam(name="name",required = false) String name,
                                 @RequestParam(name="capital",required = false) Long capital ){

        if(name!=null && capital!=null){
            return company.query(name,capital);
        }
        if(name!=null){
            return company.findCompanyByName(name);
        }
        if(capital!=null){
            return company.findCompanyByCapital(capital);
        }

        return company.findAllCompany();
    }

    @ResponseStatus(value= HttpStatus.FOUND)
    @GetMapping("/{id}")
    public CompanyDTO findOne(@PathVariable long id) {
        return company.findOneCompany(id);
    }

    @ResponseStatus(value= HttpStatus.CREATED)
    @PostMapping
    public CompanyDTO create(@RequestBody @Valid CompanyDTO c){
        return company.createCompany(c);
    }


    @ResponseStatus(value= HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        company.deleteCompany(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value= HttpStatus.OK)
    public CompanyDTO update(@PathVariable long id, @RequestBody @Valid CompanyDTO c) {
        return company.updateCompany(id,c);
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
