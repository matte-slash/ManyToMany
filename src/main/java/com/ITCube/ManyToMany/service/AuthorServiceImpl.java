package com.ITCube.ManyToMany.service;

import com.ITCube.ManyToMany.dto.AuthorDTO;
import com.ITCube.ManyToMany.exception.AuthorNotFoundException;
import com.ITCube.ManyToMany.model.Author;
import com.ITCube.ManyToMany.repository.AuthorRepository;
import com.ITCube.ManyToMany.service.interfaces.AuthorService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository author;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository author, ModelMapper modelMapper) {
        this.author = author;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AuthorDTO> findAllAuthor() {
        List<Author> result = new ArrayList<Author>();
        author.findAll().forEach(result::add);
        return result.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<AuthorDTO> findAuthorByName(String name){
        List<Author> result=new ArrayList<>();
        author.findByFirstNameIgnoreCase(name).forEach(result::add);
        return result.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO findOneAuthor(long id) {
        return author.findById(id)
                .map(this::convertToDto)
                .orElseThrow(()-> new AuthorNotFoundException("Author "+id+" not found"));
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO a) {
        return convertToDto(author.save(this.convertFromDto(a)));
    }

    @Override
    public AuthorDTO updateAuthor(long id, AuthorDTO a) {
        Author result= author.findById(id)
                        .orElseThrow(()-> new AuthorNotFoundException("Author "+id+" not found"));
        result.setFirstName(a.getFirstName());
        result.setLastName(a.getLastName());
        return convertToDto(author.save(result));
    }

    @Override
    public void deleteAuthor(long id) {
        findOneAuthor(id);
        author.deleteById(id);
    }

    // Convert Author to DTO
    private AuthorDTO convertToDto(Author a){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(a,AuthorDTO.class);
    }

    // Convert DTO to Author
    private Author convertFromDto(AuthorDTO a){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(a,Author.class);
    }

}
