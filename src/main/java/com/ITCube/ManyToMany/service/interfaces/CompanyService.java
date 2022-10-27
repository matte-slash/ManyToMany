package com.ITCube.ManyToMany.service.interfaces;

import com.ITCube.ManyToMany.exception.AuthorNotFoundException;
import com.ITCube.ManyToMany.exception.CompanyNotFoundException;
import com.ITCube.ManyToMany.model.Company;

import java.util.List;

public interface CompanyService {

    List<Company> findAll();

    Company findOne(long id) throws CompanyNotFoundException;

    Company create(Company c);

    Company update(long id, Company c) throws CompanyNotFoundException;

    void delete(long id) throws CompanyNotFoundException;

    void addAuthor(long c, long a) throws CompanyNotFoundException, AuthorNotFoundException;

    void removeAuthor(long c, long a) throws CompanyNotFoundException, AuthorNotFoundException;

    List<Company> query(String name, Long capital);

    List<Company> findByName(String name);

    List<Company> findByCapital(Long capital);
}
