package com.example.Application.Borrowing;

import com.example.Application.Author.Author;
import com.example.Application.Book.Book;
import com.example.Application.Book.BookService;
import com.example.Application.BookType.BookType;
import com.example.Application.BookType.BookTypeRepository;
import com.example.Application.Copy.Copy;
import com.example.Application.Copy.CopyRepository;
import com.example.Application.CopyAuthors.CopyAuthors;
import com.example.Application.Country.Country;
import com.example.Application.ExceptionClasses.NotFoundException;
import com.example.Application.Genre.Genre;
import com.example.Application.Genre.GenreRepository;
import com.example.Application.Member.Member;
import com.example.Application.Publisher.Publisher;
import com.example.Application.Publisher.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class BorrowingService {

    @Autowired
    BorrowingRepository borrowingRepository;
    @Autowired
    CopyRepository copyRepository;
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    PublisherRepository publisherRepository;
    @Autowired
    BookTypeRepository bookTypeRepository;
    @Autowired
    BookService bookService;

    public List<Book> GetBorrowingByMember(Integer memberId) {

        List<Tuple> dbBooks = borrowingRepository.findBorrowingByMember(memberId);

        List<Book> books = new ArrayList<>();

        for (Tuple dbBook : dbBooks) {

            Copy copy = copyRepository.findById((Integer)dbBook.get("copy_id")).get();
            Genre genre = genreRepository.findById((Integer)dbBook.get("genre_id")).get();
            Publisher publisher = publisherRepository.findById((Integer)dbBook.get("publisher_id")).get();
            BookType bookType = bookTypeRepository.findById((Integer)dbBook.get("book_type_id")).get();

            Book book = new Book((Integer)dbBook.get("id"),
                                 (String)dbBook.get("isbn"),
                                  copy,
                                  bookType,
                                  genre,
                                  publisher,
                                 (Date)dbBook.get("published_date"),
                                 (Boolean) dbBook.get("available"));

            books.add(book);
        }

        return books;
    }

    public List<Member> GetBorrowingByBook(Integer bookId) {

        List<Tuple> dbMembers = borrowingRepository.findMembersBorrowingByBook(bookId);

        List<Member> members = new ArrayList<>();

        for (Tuple dbMember : dbMembers) {

            Member member = new Member((Integer)dbMember.get("id"),
                                        (String)dbMember.get("first_name"),
                                        (String)dbMember.get("last_name"),
                                        (Boolean) dbMember.get("active"));

            members.add(member);
        }

        return members;
    }

    public Borrowing Add(Borrowing newBorrowing, String token) {

        Book book = newBorrowing.getBook();
        book.setAvailable(false);
        bookService.Update(book, book.getId(), token);

        return borrowingRepository.save(newBorrowing);
    }

    public Borrowing UpdateReturned(Integer bookId, Integer memberId, String token) {

        List<Tuple> ids = borrowingRepository.findBorrowingId(memberId, bookId);
        Integer id = (Integer)ids.get(0).get("id");

        Borrowing borrowing = borrowingRepository.findById(id)
                .map(b -> {
                    b.setReturned(true);
                    return borrowingRepository.save(b);
                })
                .orElseThrow(() -> new NotFoundException("borrowing", id));

        Book book = borrowing.getBook();
        book.setAvailable(true);
        bookService.Update(book, book.getId(), token);

       return borrowing;
    }
}
