package com.ITCube.ManyToMany.service.interfaces;

import com.ITCube.ManyToMany.dto.AuthorDTO;
import com.ITCube.ManyToMany.exception.AuthorNotFoundException;
import com.ITCube.ManyToMany.model.Author;

import java.util.List;

public interface AuthorService {

    List<AuthorDTO> findAllAuthor();

    List<AuthorDTO> findAuthorByName(String name);

    AuthorDTO findOneAuthor(long id) throws AuthorNotFoundException;

    AuthorDTO createAuthor(AuthorDTO a);

    AuthorDTO updateAuthor(long id, AuthorDTO a);

    void deleteAuthor(long id);

}
