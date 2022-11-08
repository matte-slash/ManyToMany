package com.ITCube.ManyToMany.controller;

import com.ITCube.ManyToMany.dto.CompanyDTO;
import com.ITCube.ManyToMany.exception.CompanyNotFoundException;
import com.ITCube.ManyToMany.service.CompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyControllerUnitTest {
    @Mock
    private CompanyServiceImpl service;
    @InjectMocks
    private CompanyController underTest;

    @Test
    void findCompanyTest(){
        // Arrange
        CompanyDTO expected=new CompanyDTO("Azienda",2_000);
        when(service.findAllCompany()).thenReturn(List.of(expected));

        // Action
        List<CompanyDTO> result=underTest.findCompany(null,null);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo(expected.getName());
        verify(service,times(1)).findAllCompany();
        verifyNoMoreInteractions(service);
    }

    @Test
    void findCompanyByNameTest(){
        // Arrange
        CompanyDTO expected=new CompanyDTO("Azienda",2_000);
        when(service.findCompanyByName(expected.getName())).thenReturn(List.of(expected));

        // Action
        List<CompanyDTO> result=underTest.findCompany("Azienda",null);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo(expected.getName());
        verify(service,times(1)).findCompanyByName("Azienda");
        verifyNoMoreInteractions(service);
    }

    @Test
    void findCompanyByCapitalTest(){
        // Arrange
        CompanyDTO expected=new CompanyDTO("Azienda",2_000L);
        when(service.findCompanyByCapital(expected.getCapital())).thenReturn(List.of(expected));

        // Action
        List<CompanyDTO> result=underTest.findCompany(null,2_000L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo(expected.getName());
        verify(service,times(1)).findCompanyByCapital(2_000L);
        verifyNoMoreInteractions(service);
    }

    @Test
    void findCompanyByNameAndCapital() {
        // Arrange
        CompanyDTO expected=new CompanyDTO("Azienda",2_000);
        when(service.query("Azienda",2_000L)).thenReturn(List.of(expected));

        // Action
        List<CompanyDTO> result=underTest.findCompany("Azienda",2_000L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo(expected.getName());
        verify(service,times(1)).query("Azienda",2_000L);
        verifyNoMoreInteractions(service);
    }

    @Test
    void findOneTest(){
        // Arrange
        CompanyDTO expected=new CompanyDTO("Azienda",2_000);
        when(service.findOneCompany(anyLong())).thenReturn(expected);

        // Action
        CompanyDTO result=underTest.findOne(anyLong());

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(expected.getName());
        verify(service,times(1)).findOneCompany(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void createCompanyTest(){
        // Arrange
        CompanyDTO expected=new CompanyDTO("Azienda",2_000);
        when(service.createCompany(expected)).thenReturn(expected);

        // Action
        CompanyDTO result=underTest.create(expected);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(expected.getName());
        verify(service,times(1)).createCompany(expected);
        verifyNoMoreInteractions(service);
    }

    @Test
    void deleteTest(){
        // Arrange
        doNothing().when(service).deleteCompany(anyLong());

        // Action
        underTest.delete(anyLong());

        // Assert
        verify(service,times(1)).deleteCompany(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void updateTest(){
        // Arrange
        CompanyDTO expected=new CompanyDTO("Azienda",2_000);
        when(service.updateCompany(1L,expected)).thenReturn(expected);

        // Action
        CompanyDTO result=underTest.update(1L,expected);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expected);
        verify(service,times(1)).updateCompany(anyLong(),any(CompanyDTO.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void updateFailTest(){
        // Arrange
        CompanyDTO expected=new CompanyDTO("Azienda",2_000);
        when(service.updateCompany(1L,expected)).thenThrow(CompanyNotFoundException.class);

        // Action and Assert
        assertThrows(CompanyNotFoundException.class,()->underTest.update(1L,expected));
    }

    @Test
    void addAuthorTest(){
        // Arrange
        doNothing().when(service).addAuthor(anyLong(),anyLong());

        // Action
        underTest.addAuthor(1L,1L);

        // Assert
        verify(service,times(1)).addAuthor(anyLong(),anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void removeAuthorTest(){
        // Arrange
        doNothing().when(service).removeAuthor(anyLong(),anyLong());

        // Action
        underTest.removeAuthor(1L,1L);

        // Assert
        verify(service,times(1)).removeAuthor(anyLong(),anyLong());
        verifyNoMoreInteractions(service);
    }
}
