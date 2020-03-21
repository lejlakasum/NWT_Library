package com.example.Application.CopyAuthors;

import com.example.Application.Author.Author;
import com.example.Application.Copy.Copy;
import com.example.Application.Country.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class CopyAuthorsService {

    @Autowired
    CopyAuthorsRepository copyAuthorsRepository;

    public List<Author> GetAuthorsByCopy(Integer copyId) {

        List<Object> objects = copyAuthorsRepository.findAuthorsByCopy(copyId);

        List<Author> authors = new ArrayList<Author>();
        Author author = new Author();
        author.setId(1);
        author.setFirstName("Glupan");
        author.setLastName("Tupan");
        author.setBirthDate(new Date(789456));
        Country country = new Country();
        country.setId(1);
        author.setCountry(country);
        authors.add(author);
        //Todo: Map objects to list

        return authors;
    }

    public List<Copy> GetCopiesByAuthor(Integer authorId) throws NoSuchFieldException {

        List<Object> objects = copyAuthorsRepository.findCopiesByAuthors(authorId);

        List<Copy> copies = new ArrayList<Copy>();
        Copy copy = new Copy();
        copy.setId(1);
        copy.setBookName("Kjnjigaaa");
        copies.add(copy);
        //Todo: Map objects to list

        return copies;
    }

    public CopyAuthors Add(CopyAuthors newCopyAuthors) {

        return copyAuthorsRepository.save(newCopyAuthors);
    }

    public void Delete(Integer id) {
        copyAuthorsRepository.deleteById(id);
    }
}
