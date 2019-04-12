package ru.biblio.web.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface ConversationService {
    byte[] convert(MultipartFile file) throws IOException, ExecutionException, InterruptedException;
}
