package com.ITCube.ManyToMany.controller;

import com.ITCube.ManyToMany.dto.CompanyDTO;
import com.ITCube.ManyToMany.exception.CompanyNotFoundException;
import com.ITCube.ManyToMany.service.CompanyServiceImpl;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(CompanyController.class)
public class CompanyControllerIntegrationTest {
    @Autowired
    public MockMvc mockMvc;
    @MockBean
    public CompanyServiceImpl service;

    @Test
    public void findAllCompanyTest() throws Exception {
        // Arrange
        CompanyDTO expected=new CompanyDTO("Azienda", 3_999L);
        when(service.findAllCompany()).thenReturn(List.of(expected));

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value(expected.getName()))
                .andExpect(jsonPath("$[0].capital").value(expected.getCapital()));
    }

    @Test
    public void findCompanyByCapitalTest() throws Exception {
        // Arrange
        CompanyDTO expected=new CompanyDTO("Azienda",4_001L);
        when(service.findCompanyByCapital(4_000L)).thenReturn(List.of(expected));

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/companies?capital=4000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].capital").value(expected.getCapital()));
    }

    @Test
    public void findCompanyByNameTest() throws Exception {
        // Arrange
        CompanyDTO expected=new CompanyDTO("Azienda",3_999L);
        when(service.findCompanyByName("Azienda")).thenReturn(List.of(expected));

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/companies?name=Azienda"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value(expected.getName()));

    }

    @Test
    public void queryCompanyTest() throws Exception {
        // Arrange
        CompanyDTO expected=new CompanyDTO("Azienda",4_000L);
        when(service.query("Azienda",3_999L)).thenReturn(List.of(expected));

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/companies?name=Azienda&capital=3999"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value(expected.getName()));
    }

    @Test
    public void findOneTest() throws Exception {
        // Arrange
        CompanyDTO expected=new CompanyDTO("Azienda",4_000L);
        when(service.findOneCompany(anyLong())).thenReturn(expected);

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/77"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(expected.getName()));
    }

    @Test
    public void createCompanyTest() throws Exception {
        // Arrange
        CompanyDTO expected=new CompanyDTO("Azienda",4_000L);
        when(service.createCompany(any(CompanyDTO.class))).thenReturn(expected);
        Gson gson=new Gson();

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/companies")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(expected)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(expected.getName()));
    }

    @Test
    public void deleteCompanyTest() throws Exception {
        // Arrange
        doNothing().when(service).deleteCompany(anyLong());

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/companies/77"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteCompanyFailTest() throws Exception {
        // Arrange
        doThrow(new CompanyNotFoundException("Company 77 not found"))
                .when(service).deleteCompany(anyLong());

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/companies/77"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateCompanyTest() throws Exception {
        // Arrange
        CompanyDTO expected=new CompanyDTO("Azienda",4_000L);
        when(service.updateCompany(anyLong(),any(CompanyDTO.class))).thenReturn(expected);

        Gson gson=new Gson();

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/companies/77")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(expected)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(expected.getName()));
    }

    @Test
    public void updateCompanyFailTest() throws Exception {
        // Arrange
        CompanyDTO expected=new CompanyDTO("Azienda",4_000L);
        when(service.updateCompany(anyLong(),any(CompanyDTO.class)))
                .thenThrow(CompanyNotFoundException.class);

        Gson gson=new Gson();

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/companies/77")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(expected)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void addAuthorTest() throws Exception {
        // Assert
        doNothing().when(service).addAuthor(anyLong(),anyLong());

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/companies/77/add/999"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void removeAuthorTest() throws Exception {
        // Assert
        doNothing().when(service).removeAuthor(anyLong(),anyLong());

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/companies/77/remove/999"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
