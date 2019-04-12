package ru.biblio.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.biblio.web.domain.RequestResponseBook;
import ru.biblio.web.domain.User;
import ru.biblio.web.repository.RequestResponseRepository;
import ru.biblio.web.service.RequestResponseService;

import java.util.Date;
import java.util.List;

@Service
public class RequestResponseServiceImpl implements RequestResponseService {
    @Autowired
    private RequestResponseRepository requestResponseRepository;
    @Override
    public List<RequestResponseBook> getNotAnsweredRequests(User user) {
        return this.requestResponseRepository.getRequestResponseBookByResponseAndEnabled(user,false);
    }

    @Override
    public RequestResponseBook saveRequest(RequestResponseBook requestResponseBook) {
        return this.requestResponseRepository.save(requestResponseBook);
    }

    @Override
    public RequestResponseBook getRequestResponseById(Integer id) {
        return this.requestResponseRepository.getRequestResponseBookById(id);
    }

    @Override
    public List<RequestResponseBook> getAllowedBookByRequest(User user) {
        return this.requestResponseRepository.findAllByRequestAndEnabledAndExpiredNullOrExpiredIsGreaterThan(user,true,new Date());
    }
}
