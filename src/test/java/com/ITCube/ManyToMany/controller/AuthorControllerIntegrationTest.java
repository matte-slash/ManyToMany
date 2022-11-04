package com.ITCube.ManyToMany.controller;

import com.ITCube.ManyToMany.dto.AuthorDTO;
import com.ITCube.ManyToMany.exception.AuthorNotFoundException;
import com.ITCube.ManyToMany.service.AuthorServiceImpl;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AuthorController.class)
public class AuthorControllerIntegrationTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public AuthorServiceImpl service;

    @Test
    void findAllTest() throws Exception {
        // Arrange
        AuthorDTO expected=new AuthorDTO("Matteo","Rosso");
        when(service.findAllAuthor()).thenReturn(List.of(expected));

        // Action
        mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Matteo"))
                .andExpect(jsonPath("$[0].lastName").value("Rosso"));
    }

    @Test
    void findAllTest2() throws Exception {
        // Arrange
        AuthorDTO expected=new AuthorDTO("Matteo","Rosso");
        when(service.findAuthorByName(expected.getFirstName())).thenReturn(List.of(expected));

        // Action
        mockMvc.perform(MockMvcRequestBuilders.get("/authors?name=Matteo"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Matteo"))
                .andExpect(jsonPath("$[0].lastName").value("Rosso"));
    }

    @Test
    void findOneAuthorTest() throws Exception {
        // Arrange
        AuthorDTO expected=new AuthorDTO("Matteo","Rosso");
        when(service.findOneAuthor(anyLong())).thenReturn(expected);

        // Action
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/77"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Matteo"))
                .andExpect(jsonPath("$.lastName").value("Rosso"));
    }

    @Test
    void createTest() throws Exception {
        // Arrange
        AuthorDTO expected=new AuthorDTO("Matteo","Rosso");
        when(service.createAuthor(any(AuthorDTO.class))).thenReturn(expected);

        Gson gson=new Gson();

        // Action
        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(expected)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Matteo"))
                .andExpect(jsonPath("$.lastName").value("Rosso"));
    }

    @Test
    void updateAuthorTest() throws Exception {
        // Arrange
        AuthorDTO expected=new AuthorDTO("Matteo","Rosso");
        when(service.updateAuthor(anyLong(), any(AuthorDTO.class))).thenReturn(expected);

        Gson gson=new Gson();

        // Action
        mockMvc.perform(MockMvcRequestBuilders.put("/authors/77" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(expected)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Matteo"))
                .andExpect(jsonPath("$.lastName").value("Rosso"));
    }

    @Test
    void deleteAuthorTest() throws Exception {
        // Arrange
        doNothing().when(service).deleteAuthor(anyLong());

        // Action
        mockMvc.perform(MockMvcRequestBuilders.delete("/authors/55"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void findOneAuthorFailTest() throws Exception {
        // Arrange
        when(service.findOneAuthor(anyLong())).thenThrow(AuthorNotFoundException.class);

        // Action
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/55"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
