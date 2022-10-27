package com.ITCube.ManyToMany.repository;

import com.ITCube.ManyToMany.model.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends CrudRepository<Company,Long> {
    List<Company> findByNameIgnoreCase(String name);

    List<Company> findByCapitalGreaterThan(Long capital);

    @Query("FROM Company c where c.name=?1 AND c.capital >?2")
    List<Company> findQuerySQL(String name, Long capital);

    //List<Company> findByNameIgnoreCaseAndCapitalGreaterThan(String name,Long capital);

}
