package com.example.Application.Book;

import com.example.Application.BookType.BookType;
import com.example.Application.BookType.BookTypeRepository;
import com.example.Application.Borrowing.BorrowingService;
import com.example.Application.Copy.Copy;
import com.example.Application.Copy.CopyController;
import com.example.Application.Copy.CopyRepository;
import com.example.Application.Country.CountryController;
import com.example.Application.ExceptionClasses.NotFoundException;
import com.example.Application.Genre.Genre;
import com.example.Application.Genre.GenreRepository;
import com.example.Application.Impression.Impression;
import com.example.Application.Impression.ImpressionDTO;
import com.example.Application.Impression.ImpressionService;
import com.example.Application.Member.Member;
import com.example.Application.Member.MemberController;
import com.example.Application.Member.MemberModelAssembler;
import com.example.Application.Member.MemberRepository;
import com.example.Application.Publisher.Publisher;
import com.example.Application.Publisher.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    BookModelAssembler assembler;
    @Autowired
    CopyRepository copyRepository;
    @Autowired
    BookTypeRepository bookTypeRepository;
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    PublisherRepository  publisherRepository;
    @Autowired
    BorrowingService borrowingService;
    @Autowired
    MemberModelAssembler memberModelAssembler;
    @Autowired
    ImpressionService impressionService;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    RestTemplate restTemplate;

    public CollectionModel<EntityModel<Book>> GetAll() {
        List<EntityModel<Book>> books = bookRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(books,
                linkTo(methodOn(BookController.class).GetAll()).withSelfRel()); 
    }

    public ResponseEntity<EntityModel<Book>> GetById(Integer id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("book", id));

        return ResponseEntity
                .ok()
                .body(assembler.toModel(book));
    }

    public CollectionModel<EntityModel<Member>> GetMembersByBook(Integer bookId) {

        Boolean bookExist = bookRepository.existsById(bookId);
        if(!bookExist) {
            throw new NotFoundException("book", bookId);
        }

        List<EntityModel<Member>> members = borrowingService.GetBorrowingByBook(bookId).stream()
                .map(memberModelAssembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(members,
                linkTo(methodOn(BookController.class).GetMembers(bookId)).withSelfRel());
    }

    public List<ImpressionDTO> GetImpressionsByBook(Integer bookId) {
        List<ImpressionDTO> impressions = impressionService.GetImpressionsByBook(bookId);

        for (ImpressionDTO impression : impressions) {
            impression.getMember()
                    .add(linkTo(methodOn(MemberController.class)
                            .GetById(impression.getMember().getId()))
                            .withSelfRel());
        }

        return impressions;
    }

    public ResponseEntity<EntityModel<Book>> Add(Book newBook) {

        Integer copyId = newBook.getCopy().getId();
        Integer bookTypeId = newBook.getBookType().getId();
        Integer genreId = newBook.getGenre().getId();
        Integer publisherId = newBook.getPublisher().getId();

        Copy copy = copyRepository.findById(copyId)
                .orElseThrow(() -> new NotFoundException("copy", copyId));

        BookType bookType = bookTypeRepository.findById(bookTypeId)
                .orElseThrow(() -> new NotFoundException("bookType", bookTypeId));

        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new NotFoundException("genre", genreId));

        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new NotFoundException("publisher", publisherId));

        newBook.setCopy(copy);
        newBook.setBookType(bookType);
        newBook.setGenre(genre);
        newBook.setPublisher(publisher);

        EntityModel<Book> entityModel = assembler.toModel(bookRepository.save(newBook));

        ResponseEntity<EntityModel<Book>> result = ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

        BookDTO newBookDTO = new BookDTO(result.getBody().getContent().getId(), newBook.getIsbn());
        InsertBookUserService(newBookDTO);

        return result;
    }

    public List<ImpressionDTO> AddImpression(Integer bookId, ImpressionDTO newImpression) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->new NotFoundException("book", bookId));

        Integer memberId = newImpression.getMember().getId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new NotFoundException("member", memberId));

        Impression impression = new Impression( book,
                                                member,
                                                newImpression.getRating(),
                                                newImpression.getComment());

        impressionService.AddImpression(impression);

        return impressionService.GetImpressionsByBook(bookId);
    }

    public ResponseEntity<EntityModel<Book>> Update(Book newBook, Integer id) {
        Integer copyId = newBook.getCopy().getId();
        Integer bookTypeId = newBook.getBookType().getId();
        Integer genreId = newBook.getGenre().getId();
        Integer publisherId = newBook.getPublisher().getId();

        Copy copy = copyRepository.findById(copyId)
                .orElseThrow(() -> new NotFoundException("copy", copyId));

        BookType bookType = bookTypeRepository.findById(bookTypeId)
                .orElseThrow(() -> new NotFoundException("bookType", bookTypeId));

        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new NotFoundException("genre", genreId));

        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new NotFoundException("publisher", publisherId));

        Book updatedBook = bookRepository.findById(id)
                .map(book -> {
                    book.setIsbn(newBook.getIsbn());
                    book.setCopy(copy);
                    book.setBookType(bookType);
                    book.setGenre(genre);
                    book.setPublisher(publisher);
                    book.setPublishedDate(newBook.getPublishedDate());
                    book.setAvailable(newBook.getAvailable());
                    return bookRepository.save(book);
                })
                .orElseThrow(()->new NotFoundException("book", id));

        EntityModel<Book> entityModel = assembler.toModel(updatedBook);

        BookDTO newBookDto = new BookDTO(id, newBook.getIsbn());
        UpdateBookUserService(id, newBookDto);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Book>> Delete(Integer id) {

        DeleteBookUserService(id);

        bookRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    //Private helper methods

    private void InsertBookUserService(BookDTO newBook) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<BookDTO> request = new HttpEntity<>(newBook, headers);

        ResponseEntity<BookDTO> result = restTemplate.postForEntity("http://user-service/books", request, BookDTO.class);

    }

    private void UpdateBookUserService(Integer id, BookDTO newBook) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<BookDTO> request = new HttpEntity<>(newBook, headers);

        restTemplate.put("http://user-service/books/"+id, request);
    }

    private void DeleteBookUserService(Integer id) {

        restTemplate.delete("http://user-service/books/"+id);
    }
}
