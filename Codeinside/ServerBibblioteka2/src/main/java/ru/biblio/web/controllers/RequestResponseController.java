package ru.biblio.web.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.biblio.web.domain.Book;
import ru.biblio.web.domain.RequestResponseBook;
import ru.biblio.web.domain.User;
import ru.biblio.web.service.BookService;
import ru.biblio.web.service.RequestResponseService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestResponseController {

    @Autowired
    private RequestResponseService requestResponseService;

    @Autowired
    private BookService bookService;

    @RequestMapping("/get-not-answered-request")
    public List<RequestResponseBook> getNotAnsweredRequests(@RequestParam  Long userId){
        User user = new User();
        user.setId(userId);
        return this.requestResponseService.getNotAnsweredRequests(user);
    }

    @RequestMapping("/made-request-book")
    @ResponseStatus(HttpStatus.OK)
    public void madeRequestBook(@RequestParam Long userId, @RequestParam Integer bookId, @RequestParam(required = false) String message){
        RequestResponseBook requestResponseBook = new RequestResponseBook();
        requestResponseBook.setRequest(new User());
        requestResponseBook.getRequest().setId(userId);
        requestResponseBook.setMessage(message);

        Book book = new Book();
        book.setId(bookId);
        book = this.bookService.getBookById(book);
        User response = new User();
        response.setId(book.getOwner().getId());
        requestResponseBook.setResponse(response);
        requestResponseBook.setBook(book);
        this.requestResponseService.saveRequest(requestResponseBook);
    }

    @RequestMapping("/made-response-book")
    @ResponseStatus(HttpStatus.OK)
    public void madeResponseBook(@RequestParam  Integer responseRequestId,
                                 @RequestParam(required = false) String answer,
                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date expired,
                                 @RequestParam(required = false) Boolean enabled){
        if(enabled == null) {
            enabled = false;
        }

        if(!enabled && StringUtils.isEmpty(answer)){
            answer = "Access denied";
        }
        RequestResponseBook requestResponseBook = this.requestResponseService.getRequestResponseById(responseRequestId);
        requestResponseBook.setAnswer(answer);
        requestResponseBook.setExpired(expired);
        requestResponseBook.setEnabled(enabled);
        this.requestResponseService.saveRequest(requestResponseBook);
    }


}
