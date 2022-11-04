package com.ITCube.ManyToMany.controller;

import com.ITCube.ManyToMany.dto.AuthorDTO;
import com.ITCube.ManyToMany.exception.AuthorNotFoundException;
import com.ITCube.ManyToMany.service.AuthorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerUnitTest {
    @Mock
    private AuthorServiceImpl service;
    @InjectMocks
    private AuthorController underTest;

    @Test
    void findAllTest(){
        // Arrange
        AuthorDTO expected=new AuthorDTO("Matteo","Rosso");
        when(service.findAllAuthor()).thenReturn(List.of(expected));

        // Action
        List<AuthorDTO> result=underTest.findAll(null);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getFirstName()).isEqualTo(expected.getFirstName());
        verify(service,times(1)).findAllAuthor();
        verifyNoMoreInteractions(service);
    }

    @Test
    void findAllTest2(){
        // Arrange
        AuthorDTO expected=new AuthorDTO("Matteo","Rosso");
        when(service.findAuthorByName(expected.getFirstName())).thenReturn(List.of(expected));

        // Action
        List<AuthorDTO> result=underTest.findAll(expected.getFirstName());

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getFirstName()).isEqualTo(expected.getFirstName());
        verify(service,times(1)).findAuthorByName(expected.getFirstName());
        verifyNoMoreInteractions(service);
    }

    @Test
    void findOneAuthorTest(){
        // Arrange
        AuthorDTO expected=new AuthorDTO("Matteo","Rosso");
        when(service.findOneAuthor(anyLong())).thenReturn(expected);

        // Action
        AuthorDTO result=underTest.findOneAuthor(anyLong());

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo(expected.getFirstName());
        verify(service,times(1)).findOneAuthor(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void createTest(){
        // Arrange
        AuthorDTO expected=new AuthorDTO("Matteo","Rosso");
        when(service.createAuthor(expected)).thenReturn(expected);

        // Action
        AuthorDTO result=underTest.create(expected);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expected);
        verify(service,times(1)).createAuthor(expected);
        verifyNoMoreInteractions(service);
    }

    @Test
    void updateAuthorTest(){
        // Arrange
        AuthorDTO expected=new AuthorDTO("Matteo","Rosso");
        when(service.updateAuthor(1L,expected)).thenReturn(expected);

        // Action
        AuthorDTO result=underTest.updateAuthor(1L,expected);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expected);
        verify(service,times(1)).updateAuthor(1L,expected);
        verifyNoMoreInteractions(service);
    }

    @Test
    void UpdateAuthorFailTest(){
        // Arrange
        AuthorDTO expected=new AuthorDTO("Matteo","Rosso");
        when(service.updateAuthor(1L,expected)).thenThrow(AuthorNotFoundException.class);

        // Action    Assert
        assertThrows(AuthorNotFoundException.class,()-> underTest.updateAuthor(1L,expected));
    }

    @Test
    void deleteAuthorTest(){
        // Arrange
        AuthorDTO expected=new AuthorDTO("Matteo","Rosso");
        doNothing().when(service).deleteAuthor(anyLong());

        // Action
        underTest.deleteAuthor(anyLong());

        // Assert
        verify(service,times(1)).deleteAuthor(anyLong());
        verifyNoMoreInteractions(service);
    }
}
