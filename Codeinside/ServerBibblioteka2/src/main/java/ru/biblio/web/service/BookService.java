package ru.biblio.web.service;

import ru.biblio.web.domain.*;

import java.io.IOException;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> getAnotherPeopleBooks(User user, Shelf shelf);

    List<Book> getOwnBooks(User user);

    Book getBookById(Book book);

    Book update(Book book);

    BookDTO conversationInBookDTO(Book book,Page page) throws IOException;

    void setCurrentPage(User user, Book book, Page page);

    ProgressReading getCurrentPage(User user, Book book);

}
