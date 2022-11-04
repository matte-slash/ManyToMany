package com.ITCube.ManyToMany.service;

import com.ITCube.ManyToMany.dto.AuthorDTO;
import com.ITCube.ManyToMany.exception.AuthorNotFoundException;
import com.ITCube.ManyToMany.model.Author;
import com.ITCube.ManyToMany.repository.AuthorRepository;
import com.ITCube.ManyToMany.service.interfaces.AuthorService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplUnitTest {

    @Mock
    private AuthorRepository rep;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private AuthorServiceImpl underTest;

    @BeforeEach
    public void setUp(){
        Configuration configurationMock = mock(Configuration.class);
        lenient().when(configurationMock.setMatchingStrategy(MatchingStrategies.LOOSE))
                .thenReturn(configurationMock);

        lenient().when(modelMapper.getConfiguration()).thenReturn(configurationMock);
    }

    @Test
    void findAllAuthorTest() {
        // When
        when(rep.findAll()).thenReturn(List.of(new Author("Matteo", "Rosso")));

        // Assert
        assertFalse(underTest.findAllAuthor().isEmpty());
        assertThat(underTest.findAllAuthor().size(), equalTo(1));
        verify(rep,times(2)).findAll();
        verifyNoMoreInteractions(rep);
    }

    @Test
    void findAuthorByNameTest() {
        // When
        Author expected=new Author("Matteo","Rosso");
        when(rep.findByFirstNameIgnoreCase(expected.getFirstName())).thenReturn(List.of(expected));
        when(modelMapper.map(expected,AuthorDTO.class)).thenReturn(new AuthorDTO("Matteo","Rosso"));

        // Action
        AuthorDTO actual=underTest.findAuthorByName("Matteo").get(0);

        // Assert
        assertEquals(expected.getLastName(),actual.getLastName());
        verify(rep,times(1)).findByFirstNameIgnoreCase(anyString());
        verifyNoMoreInteractions(rep);
    }

    @Test
    void findOneAuthorTest() {
        // When
        Author expected=new Author("Matteo","Rosso");
        when(rep.findById(anyLong())).thenReturn(Optional.of(expected));
        when(modelMapper.map(expected,AuthorDTO.class)).thenReturn(new AuthorDTO("Matteo","Rosso"));

        // Action
        AuthorDTO actual=underTest.findOneAuthor(anyLong());

        // Assert
        assertEquals(expected.getLastName(),actual.getLastName());
        verify(rep,times(1)).findById(anyLong());
        verifyNoMoreInteractions(rep);
    }

    @Test
    void findOneAuthorFailTest() {
        // When
        when(rep.findById(anyLong())).thenReturn(Optional.empty());

        // Assert
        assertThrows(AuthorNotFoundException.class,()->underTest.findOneAuthor(2));
    }

    @Test
    void createAuthorTest() {
        // When
        Author expected=new Author("Matteo","Rosso");
        AuthorDTO dto=new AuthorDTO("Matteo","Rosso");
        when(rep.save(any(Author.class))).thenReturn(expected);
        when(modelMapper.map(expected,AuthorDTO.class)).thenReturn(dto);
        when(modelMapper.map(dto,Author.class)).thenReturn(expected);

        // Action
        AuthorDTO actual=underTest.createAuthor(dto);

        // Assert
        assertEquals(expected.getLastName(),actual.getLastName());
        verify(rep,times(1)).save(any(Author.class));
        verifyNoMoreInteractions(rep);
    }

    @Test
    void updateAuthorTest() {

        // When
        Author expected=new Author("Matteo","Rosso");
        Author new_a=new Author("Nuovo","Rosso");
        AuthorDTO dto=new AuthorDTO("Nuovo","Rosso");
        when(rep.findById(anyLong())).thenReturn(Optional.of(expected));
        when(rep.save(any(Author.class))).thenReturn(new_a);
        when(modelMapper.map(new_a,AuthorDTO.class)).thenReturn(dto);

        // Action
        AuthorDTO actual=underTest.updateAuthor(anyLong(),dto);

        // Assert
        assertEquals(actual.getFirstName(),new_a.getFirstName());
        verify(rep,times(1)).save(any(Author.class));
        verify(rep,times(1)).findById(anyLong());
        verifyNoMoreInteractions(rep);
    }

    @Test
    void deleteAuthor() {
        // When
        doNothing().when(rep).deleteById(anyLong());
        when(rep.findById(anyLong())).thenReturn(Optional.of(new Author()));

        // Action
        underTest.deleteAuthor(anyLong());

        // Assert
        verify(rep,times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(rep);
    }
}