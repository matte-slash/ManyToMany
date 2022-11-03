package com.ITCube.ManyToMany.service;

import com.ITCube.ManyToMany.dto.CompanyDTO;
import com.ITCube.ManyToMany.exception.AuthorNotFoundException;
import com.ITCube.ManyToMany.exception.CompanyNotFoundException;
import com.ITCube.ManyToMany.model.Author;
import com.ITCube.ManyToMany.model.Company;
import com.ITCube.ManyToMany.repository.AuthorRepository;
import com.ITCube.ManyToMany.repository.CompanyRepository;
import com.ITCube.ManyToMany.service.interfaces.CompanyService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository company;
    private final AuthorRepository author;
    private final ModelMapper modelMapper;

    @Autowired
    public CompanyServiceImpl(CompanyRepository company, AuthorRepository author, ModelMapper modelMapper) {
        this.company = company;
        this.author = author;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CompanyDTO> findAllCompany() {
        List<Company> result = new ArrayList<Company>();
        company.findAll().forEach(result::add);
        return result.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDTO findOneCompany(long id) {
        return company.findById(id)
                .map(this::convertToDto)
                .orElseThrow(()->new CompanyNotFoundException("Company "+id+" not found"));

    }

    @Override
    public CompanyDTO createCompany(CompanyDTO c) {
        return convertToDto(company.save(convertFromDto(c)));
    }

    @Override
    public CompanyDTO updateCompany(long id, CompanyDTO c) {
        Company result=company.findById(id)
                        .orElseThrow(()->new CompanyNotFoundException(("Company "+id+" not found")));
        result.setName(c.getName());
        return convertToDto(company.save(result));
    }

    @Override
    public void deleteCompany(long id) {
        company.findById(id)
                .orElseThrow(()->new CompanyNotFoundException("Company "+id+" not found"));
        company.deleteById(id);
    }

    @Transactional
    public void addAuthor(long idc, long ida) {
        Company c_result=company.findById(idc)
                .orElseThrow(()->new CompanyNotFoundException("Company "+idc+" not found"));
        Author a_result=author.findById(ida)
                .orElseThrow(()->new AuthorNotFoundException("Author "+ida+" not found"));
        c_result.addAuthor(a_result);
    }

    @Transactional
    public void removeAuthor(long idc, long ida) {
        Company c_result=company.findById(idc)
                .orElseThrow(()->new CompanyNotFoundException("Company "+idc+" not found"));
        Author a_result=author.findById(ida)
                        .orElseThrow(()->new AuthorNotFoundException("Author "+ida+" not found"));
        c_result.removeAuthor(a_result);
    }

    @Override
    public List<CompanyDTO> query(String name, Long capital) {
        return company.findQuerySQL(name,capital)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CompanyDTO> findCompanyByName(String name) {
        return company.findByNameIgnoreCase(name)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CompanyDTO> findCompanyByCapital(Long capital) {
        return company.findByCapitalGreaterThan(capital)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private CompanyDTO convertToDto(Company c) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(c, CompanyDTO.class);
    }

    private Company convertFromDto(CompanyDTO c) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(c, Company.class);
    }

}
