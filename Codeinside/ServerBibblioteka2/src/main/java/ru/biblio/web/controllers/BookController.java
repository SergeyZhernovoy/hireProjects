package ru.biblio.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.biblio.web.domain.*;
import ru.biblio.web.service.BookService;
import ru.biblio.web.service.ConversationService;
import ru.biblio.web.service.RequestResponseService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private BookService bookService;

    @Autowired
    private RequestResponseService requestResponseService;

    @RequestMapping("/save")
    public ResponseEntity<?> save(@RequestParam String name,
                     @RequestParam String author,
                     @RequestParam Long shelfId ,
                     @RequestParam MultipartFile file,
                     @RequestParam Long userId) {

        Book book = new Book();
        User user = new User();
        Shelf shelf = new Shelf();
        shelf.setId(shelfId);
        user.setId(userId);

        if (file.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {
            byte[] conversationText = this.conversationService.convert(file);
            book.setAuthor(author);
            book.setTitle(name);
            book.setOwner(user);
            book.setShelf(shelf);
            book.setBlob(conversationText);

            if (conversationText.length >= file.getSize() ){
                this.bookService.save(book);
            }
        } catch (IOException | InterruptedException | ExecutionException e ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - " +
                file.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping("/search-another-people-book")
    public List<Book> getAnotherPeopleBooks(@RequestParam Long userId, @RequestParam Long shelfId){
            Shelf shelf = new Shelf();
            User user = new User();
            shelf.setId(shelfId);
            user.setId(userId);
            return this.bookService.getAnotherPeopleBooks(user,shelf);
    }

    @RequestMapping("/get-allowed-book")
    public List<Book> getAllowedBooks(@RequestParam Long userId){
        User user = new User();
        user.setId(userId);

        List<Book> books = this.bookService.getOwnBooks(user);
        Stream<Book> allowedBook = this
                .requestResponseService
                .getAllowedBookByRequest(user)
                .stream()
                .map(requestResponseBook -> requestResponseBook.getBook());

        return Stream
                .concat(allowedBook,books.stream())
                .collect(Collectors.toList());
    }

    @RequestMapping("/change-shelf")
    @ResponseStatus(HttpStatus.OK)
    public void changeShelf(@RequestParam Integer bookId, @RequestParam Long shelfId){
           Book book = new Book();
           book.setId(bookId);
           book = this.bookService.getBookById(book);
           book.setShelf(new Shelf());
           book.getShelf().setId(shelfId);
           this.bookService.update(book);
    }


    @RequestMapping("/open")
    public ResponseEntity<BookDTO> read(@RequestParam Integer bookId, @RequestParam Long userId) throws IOException {
        Book book = new Book();
        book.setId(bookId);
        book = this.bookService.getBookById(book);

        User user = new User();
        user.setId(userId);
        Page page = new Page();
        ProgressReading currentProgress = this.bookService.getCurrentPage(user,book);
        if(currentProgress == null){
            page.setPageIndex(1);
            this.bookService.setCurrentPage(user,book,page);
        } else {
            page.setPageIndex(currentProgress.getPage());
        }
        BookDTO bookDTO = this.bookService.conversationInBookDTO(book,page);
        return ResponseEntity
                .ok(bookDTO);
    }

    @RequestMapping("/book-page")
    @ResponseStatus(HttpStatus.OK)
    public void nextPage(@RequestParam Integer bookId, @RequestParam Long userId, @RequestParam Integer index) throws IOException {
        Book book = new Book();
        book.setId(bookId);

        User user = new User();
        user.setId(userId);

        Page page = new Page();
        page.setPageIndex(index);

        ProgressReading currentProgress = this.bookService.getCurrentPage(user,book);
        if(currentProgress == null){
            page.setPageIndex(1);
        }
        this.bookService.setCurrentPage(user,book,page);
    }

}
