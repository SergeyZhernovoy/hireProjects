package ru.biblio.web.service.conversation;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

public interface FormatIncomingFile extends Callable<byte[]> {
    void conversation(MultipartFile file, File directory) throws IOException;
}
