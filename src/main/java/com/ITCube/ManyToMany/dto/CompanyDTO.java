package com.ITCube.ManyToMany.dto;

import com.ITCube.ManyToMany.model.Author;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

public class CompanyDTO {

    private long id;

    @NotBlank
    private String name;

    @Min(1000)
    private long capital;

    @JsonIgnoreProperties("companies")
    private Set<Author> authors;

    public CompanyDTO() {
    }

    public CompanyDTO(long id, String name, long capital, Set<Author> authors) {
        this.id = id;
        this.name = name;
        this.capital = capital;
        this.authors = authors;
    }

    public CompanyDTO(String name, long capital) {
        this.name = name;
        this.capital = capital;
        this.authors=new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCapital() {
        return capital;
    }

    public void setCapital(long capital) {
        this.capital = capital;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }
}
