package com.ITCube.ManyToMany.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {

    @Test
    void createCompanyTest(){
        Company company=new Company("Azienda",45_000);
        assertNotNull(company);
        assertEquals(company.getName(),"Azienda");
    }
}