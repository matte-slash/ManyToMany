package com.ITCube.ManyToMany.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthorTest {

    @Test
    void createAuthorTest(){
        Author author=new Author("Matteo","Rosso");
        assertNotNull(author);
        assertEquals(author.getFirstName(),"Matteo");
    }

}