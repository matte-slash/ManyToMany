package com.ITCube.ManyToMany.repository;

import com.ITCube.ManyToMany.model.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends CrudRepository<Company,Long> {
}
