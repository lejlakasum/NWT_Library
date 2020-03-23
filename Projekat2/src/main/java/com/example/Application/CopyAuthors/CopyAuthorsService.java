package com.example.Application.CopyAuthors;

import com.example.Application.Author.Author;
import com.example.Application.Copy.Copy;
import com.example.Application.Country.Country;
import com.example.Application.Country.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CopyAuthorsService {

    @Autowired
    CopyAuthorsRepository copyAuthorsRepository;
    @Autowired
    CountryRepository countryRepository;

    public List<Author> GetAuthorsByCopy(Integer copyId) {

        List<Tuple> dbAuthors = copyAuthorsRepository.findAuthorsByCopy(copyId);

        List<Author> authors = new ArrayList<Author>();

        for (Tuple dbAuthor : dbAuthors) {

            Integer country_id = (Integer)dbAuthor.get("country_id");
            Optional<Country> country = countryRepository.findById(country_id);

            Author author = new Author( (Integer)dbAuthor.get("id"),
                                        (String)dbAuthor.get("first_name"),
                                        (String)dbAuthor.get("last_name"),
                                        (Date)dbAuthor.get("birth_date"),
                                        country.get());

            authors.add(author);
        }

        return authors;
    }

    public List<Copy> GetCopiesByAuthor(Integer authorId) throws NoSuchFieldException {

        List<Tuple> dbCopies = copyAuthorsRepository.findCopiesByAuthors(authorId);

        List<Copy> copies = new ArrayList<>();

        for (Tuple dbCopy : dbCopies) {

            Copy copy = new Copy( (Integer)dbCopy.get("id"),
                                  (String)dbCopy.get("book_name"));

            copies.add(copy);
        }

        return copies;
    }

    public CopyAuthors Add(CopyAuthors newCopyAuthors) {

        return copyAuthorsRepository.save(newCopyAuthors);
    }

    public ResponseEntity<EntityModel<CopyAuthors>> Delete(Integer copyId, Integer authorId) {

        List<Tuple> ids = copyAuthorsRepository.findCopyAuthorId(copyId, authorId);
        Integer id = (Integer) ids.get(0).get("id");

        copyAuthorsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public void DeleteById(Integer id) {
        copyAuthorsRepository.deleteById(id);
    }
}
