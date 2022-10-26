package com.ITCube.ManyToMany.service;

import com.ITCube.ManyToMany.exception.AuthorNotFoundException;
import com.ITCube.ManyToMany.exception.CompanyNotFoundException;
import com.ITCube.ManyToMany.model.Author;
import com.ITCube.ManyToMany.model.Company;
import com.ITCube.ManyToMany.repository.CompanyRepository;
import com.ITCube.ManyToMany.service.interfaces.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository company;
    private final AuthorServiceImpl author;

    @Autowired
    public CompanyServiceImpl(CompanyRepository company, AuthorServiceImpl author) {
        this.company = company;
        this.author = author;
    }

    @Override
    public List<Company> findAll() {
        List<Company> result = new ArrayList<Company>();
        company.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Company findOne(long id) throws CompanyNotFoundException {

        return company.findById(id).orElseThrow(()->new CompanyNotFoundException("Company "+id+" not found"));

    }

    @Override
    public Company create(Company c) {
        return company.save(c);
    }

    @Override
    public Company update(long id, Company c) throws CompanyNotFoundException {
        Company result=findOne(id);
        result.setName(c.getName());
        return company.save(result);
    }

    @Override
    public void delete(long id) throws CompanyNotFoundException {
        Company result=findOne(id);
        company.deleteById(id);
    }

    @Transactional
    public void addAuthor(long idc, long ida) throws CompanyNotFoundException, AuthorNotFoundException {
        Company c_result=findOne(idc);
        Author a_result=author.findOne(ida);
        c_result.addAuthor(a_result);
    }

    @Transactional
    public void removeAuthor(long idc, long ida) throws CompanyNotFoundException, AuthorNotFoundException {
        Company c_result=findOne(idc);
        Author a_result=author.findOne(ida);
        c_result.removeAuthor(a_result);
    }
}
