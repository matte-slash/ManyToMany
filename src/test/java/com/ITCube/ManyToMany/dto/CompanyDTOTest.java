package com.ITCube.ManyToMany.dto;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyDTOTest {

    private final Validator validator= Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void nameMustBeNotBlank(){
        CompanyDTO company=new CompanyDTO("",9_000);
        Set<ConstraintViolation<CompanyDTO>> violations = validator.validate(company);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void capitalMustBeGreaterThanThousand(){
        CompanyDTO company=new CompanyDTO("Azienda", 999);
        Set<ConstraintViolation<CompanyDTO>> violations = validator.validate(company);
        assertThat(violations).isNotEmpty();
    }


}