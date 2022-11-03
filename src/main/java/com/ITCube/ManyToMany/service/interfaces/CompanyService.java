package com.ITCube.ManyToMany.service.interfaces;

import com.ITCube.ManyToMany.dto.CompanyDTO;
import com.ITCube.ManyToMany.exception.AuthorNotFoundException;
import com.ITCube.ManyToMany.exception.CompanyNotFoundException;
import com.ITCube.ManyToMany.model.Company;

import java.util.List;

public interface CompanyService {

    List<CompanyDTO> findAllCompany();

    CompanyDTO findOneCompany(long id);

    CompanyDTO createCompany(CompanyDTO c);

    CompanyDTO updateCompany(long id, CompanyDTO c);

    void deleteCompany(long id);

    void addAuthor(long c, long a);

    void removeAuthor(long c, long a);

    List<CompanyDTO> query(String name, Long capital);

    List<CompanyDTO> findCompanyByName(String name);

    List<CompanyDTO> findCompanyByCapital(Long capital);
}
