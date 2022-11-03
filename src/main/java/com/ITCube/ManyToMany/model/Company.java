package com.ITCube.ManyToMany.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "c_name", columnDefinition ="TEXT", nullable = false)
    private String name;

    @Column(name="capital", nullable=false)
    private long capital;

    @ManyToMany(cascade = CascadeType.ALL, targetEntity= Author.class,fetch = FetchType.EAGER)
    @JoinTable(name="company_author",
        joinColumns = {@JoinColumn(name="Company_id")},
        inverseJoinColumns = {@JoinColumn(name="Author_id")})
    private Set<Author> authors;

    public Company() {
    }

    public Company(String name,long capital) {
        this.name = name;
        this.capital = capital;
        this.authors = new HashSet<Author>();
    }

    public void addAuthor(Author a){
        this.authors.add(a);
        a.getCompanies().add(this);
    }

    public void removeAuthor(Author a){
        this.authors.remove(a);
        a.getCompanies().remove(this);
    }

    public long getCapital() {
        return capital;
    }

    public void setCapital(long capital) {
        this.capital = capital;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capital=" + capital +
                ", authors=" + authors +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }
}
