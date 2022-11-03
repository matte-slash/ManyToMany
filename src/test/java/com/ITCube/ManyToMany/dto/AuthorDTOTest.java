package com.ITCube.ManyToMany.dto;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorDTOTest {

    private final Validator validator= Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void nameMustBeNotBlank(){
        AuthorDTO author=new AuthorDTO("","Rosso");
        Set<ConstraintViolation<AuthorDTO>> violations = validator.validate(author);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void nameMustBeNotNull(){
        AuthorDTO author=new AuthorDTO(null,"Rosso");
        Set<ConstraintViolation<AuthorDTO>> violations = validator.validate(author);
        assertThat(violations).isNotEmpty();
    }

}