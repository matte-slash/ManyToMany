package com.ITCube.ManyToMany.service;

import com.ITCube.ManyToMany.dto.AuthorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthorServiceImplIntegrationTest {

    @Autowired
    private AuthorServiceImpl underTest;

    @Test
    void findAllAuthorTest(){
        // Arrange
        AuthorDTO expected=new AuthorDTO("Luca","Rosso");
        underTest.createAuthor(expected);

        // Action
        List<AuthorDTO> result=underTest.findAllAuthor();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getFirstName()).isEqualTo(expected.getFirstName());
    }

    @Test
    void testCreate() {
        //Arrange
        AuthorDTO expected = new AuthorDTO("Matteo", "Rosso");

        //Action
        AuthorDTO found = underTest.createAuthor(expected);

        //Assert
        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo(expected.getFirstName());
        assertThat(found.getLastName()).isEqualTo(expected.getLastName());
    }



    @Test
    void findAuthorByNameTest(){
        //Arrange
        AuthorDTO expected=new AuthorDTO("Matteo","Rosso");
        underTest.createAuthor(expected);

        // Action
        List<AuthorDTO> result=underTest.findAuthorByName(expected.getFirstName());

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getFirstName()).isEqualTo(expected.getFirstName());
    }

    @Test
    void findOneAuthorTest(){
        // Arrange
        AuthorDTO expected=new AuthorDTO("Matteo","Rosso");
        expected=underTest.createAuthor(expected);

        // Action
        AuthorDTO result= underTest.findOneAuthor(expected.getId());

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getLastName()).isEqualTo(expected.getLastName());
    }

    @Test
    void updateAuthorTest(){
        // Arrange
        AuthorDTO a1=new AuthorDTO("Matteo","Rosso");
        AuthorDTO a2=new AuthorDTO("Luca","Rosso");
        a1=underTest.createAuthor(a1);

        // Action
        AuthorDTO result= underTest.updateAuthor(a1.getId(),a2);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo(a2.getFirstName());
    }

    @Test
    void deleteAuthorTest(){
        // Arrange
        AuthorDTO expected=new AuthorDTO("Matteo","Rosso");
        expected=underTest.createAuthor(expected);

        // Action
        underTest.deleteAuthor(expected.getId());
        List<AuthorDTO> result=underTest.findAllAuthor();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(0);

    }

}
