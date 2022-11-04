package com.ITCube.ManyToMany.dto;

import com.ITCube.ManyToMany.model.Company;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

public class AuthorDTO {

    private long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @JsonIgnoreProperties("authors")
    private Set<CompanyDTO> companies;

    public AuthorDTO() {
    }

    public AuthorDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.companies=new HashSet<>();
    }

    public AuthorDTO(long id, String firstName, String lastName, Set<CompanyDTO> companies) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companies = companies;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<CompanyDTO> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<CompanyDTO> companies) {
        this.companies = companies;
    }
}
