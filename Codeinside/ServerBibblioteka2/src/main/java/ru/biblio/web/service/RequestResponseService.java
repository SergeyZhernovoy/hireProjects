package ru.biblio.web.service;

import ru.biblio.web.domain.RequestResponseBook;
import ru.biblio.web.domain.User;

import java.util.Date;
import java.util.List;

public interface RequestResponseService {
    List<RequestResponseBook> getNotAnsweredRequests(User user);

    RequestResponseBook saveRequest(RequestResponseBook requestResponseBook);

    RequestResponseBook getRequestResponseById(Integer id);

    List<RequestResponseBook> getAllowedBookByRequest(User user);


}
