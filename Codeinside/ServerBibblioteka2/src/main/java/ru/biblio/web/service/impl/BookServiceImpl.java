package ru.biblio.web.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.biblio.web.domain.*;
import ru.biblio.web.repository.BookRepository;
import ru.biblio.web.repository.ProgressReadingRepository;
import ru.biblio.web.service.BookService;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ProgressReadingRepository progressReadingRepository;

    @Override
    @Transactional
    @Cacheable("book")
    public Book save(Book book) {
        return this.bookRepository.save(book);
    }

    @Override
    public List<Book> getAnotherPeopleBooks(User user, Shelf shelf) {
        return this.bookRepository.findBookByOwnerIsNotAndShelf(user,shelf);
    }

    @Override
    public List<Book> getOwnBooks(User user) {
        return this.bookRepository.findBookByOwnerOrderByShelf(user);
    }

    @Override
    @Cacheable("book")
    public Book getBookById(Book book) {
        return this.bookRepository.findOne(book.getId());
    }

    @Override
    @Transactional
    @CacheEvict(value = "book",allEntries = true)
    public Book update(Book book) {
        return this.bookRepository.saveAndFlush(book);
    }

    @Override
    public BookDTO conversationInBookDTO(Book book,Page startPage) throws IOException {

        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(book.getId());
        bookDTO.setUserId(book.getOwner().getId());
        Document docHtml = Jsoup.parse(new String(book.getBlob(), StandardCharsets.UTF_8));
        Elements htmlPages = docHtml.getElementsByClass("page");
        int index = 0;
        for(Element htmlPage : htmlPages){
            Page page = new Page();
            page.setContent(htmlPage.toString());
            page.setPageIndex(++index);
            bookDTO.setContentPage(page);
        }
        Elements htmlStyle = docHtml.getElementsByTag("style");
        bookDTO.setStyle(htmlStyle.html());
        return bookDTO;
    }

    @Override
    public void setCurrentPage(User user, Book book, Page page) {
        ProgressReading progressReading = this.getCurrentPage(user,book);
        if(progressReading == null){
            progressReading = new ProgressReading();
            progressReading.setBook(book);
            progressReading.setUser(user);
        }
        progressReading.setPage(page.getPageIndex());
        this.progressReadingRepository.save(progressReading);
    }

    @Override
    @Cacheable("page")
    public ProgressReading getCurrentPage(User user, Book book) {
        return this.progressReadingRepository.findByUserAndAndBook(user,book);
    }
}
