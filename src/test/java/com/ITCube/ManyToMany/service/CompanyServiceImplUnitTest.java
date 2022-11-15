package com.ITCube.ManyToMany.service;

import com.ITCube.ManyToMany.dto.CompanyDTO;
import com.ITCube.ManyToMany.exception.CompanyNotFoundException;
import com.ITCube.ManyToMany.model.Company;
import com.ITCube.ManyToMany.repository.AuthorRepository;
import com.ITCube.ManyToMany.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceImplUnitTest {
    @Mock
    private CompanyRepository c_repo;
    @Mock
    private AuthorRepository a_repo;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private CompanyServiceImpl underTest;

    @BeforeEach
    public void setUp() {
        Configuration configurationMock = mock(Configuration.class);
        lenient().when(configurationMock.setMatchingStrategy(MatchingStrategies.LOOSE))
                .thenReturn(configurationMock);

        lenient().when(modelMapper.getConfiguration()).thenReturn(configurationMock);
    }

    @Test
    void findAllAuthorTest(){
        // When
        Company expected=new Company("Apple",9_000L);
        when(c_repo.findAll()).thenReturn(List.of(expected));
        when(modelMapper.map(expected,CompanyDTO.class))
                .thenReturn(new CompanyDTO("Apple",9_000L));

        // Action
        List<CompanyDTO> result=underTest.findAllCompany();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        verify(c_repo,times(1)).findAll();
        verifyNoMoreInteractions(c_repo);
    }

    @Test
    void findOneCompanyTest(){
        // When
        Company expected=new Company("Apple",9_000L);
        when(c_repo.findById(anyLong())).thenReturn(Optional.of(expected));
        when(modelMapper.map(expected,CompanyDTO.class))
                .thenReturn(new CompanyDTO("Apple",9_000L));

        // Action
        CompanyDTO result=underTest.findOneCompany(anyLong());

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(expected.getName());
        verify(c_repo,times(1)).findById(anyLong());
        verifyNoMoreInteractions(c_repo);
    }

    @Test
    void createCompanyTest(){
        // When
        Company expected=new Company("Apple",9_000L);
        CompanyDTO dto=new CompanyDTO("Apple",9_000L);
        when(c_repo.save(expected)).thenReturn(expected);
        when(modelMapper.map(expected,CompanyDTO.class)).thenReturn(dto);
        when(modelMapper.map(dto,Company.class)).thenReturn(expected);

        // Action
        CompanyDTO result=underTest.createCompany(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCapital()).isEqualTo(dto.getCapital());
        verify(c_repo,times(1)).save(expected);
        verifyNoMoreInteractions(c_repo);
    }

    @Test
    void updateCompanyTest(){
        // When
        Company expected=new Company("Apple",9_000L);
        Company c_new=new Company("Google",9_001L);
        CompanyDTO dto=new CompanyDTO("Google",9_001L);
        when(c_repo.findById(anyLong())).thenReturn(Optional.of(expected));
        when(c_repo.save(any(Company.class))).thenReturn(c_new);
        when(modelMapper.map(c_new,CompanyDTO.class)).thenReturn(dto);

        // Action
        CompanyDTO result=underTest.updateCompany(anyLong(),dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCapital()).isEqualTo(dto.getCapital());
        verify(c_repo,times(1)).save(any(Company.class));
        verifyNoMoreInteractions(c_repo);
    }

    @Test
    void deleteCompanyTest(){
        // When
        Company expected=new Company("Apple",9_000L);
        when(c_repo.findById(anyLong())).thenReturn(Optional.of(expected));
        doNothing().when(c_repo).deleteById(anyLong());

        // Action
        underTest.deleteCompany(anyLong());

        // Assert
        verify(c_repo,times(1)).deleteById(anyLong());
        verify(c_repo,times(1)).findById(anyLong());
        verifyNoMoreInteractions(c_repo);
    }

    @Test
    void deleteCompanyFailTest(){
        // When
        doThrow(CompanyNotFoundException.class).when(c_repo).findById(anyLong());

        // Assert and Action
        assertThrows(CompanyNotFoundException.class,()-> underTest.deleteCompany(anyLong()));
        verify(c_repo,times(1)).findById(anyLong());
        verifyNoMoreInteractions(c_repo);
    }


}
