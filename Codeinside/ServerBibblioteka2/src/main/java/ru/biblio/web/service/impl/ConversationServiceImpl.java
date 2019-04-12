package ru.biblio.web.service.impl;

import com.fasterxml.jackson.core.io.UTF8Writer;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.io.RandomAccessOutputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.biblio.web.service.ConversationService;
import ru.biblio.web.service.conversation.FormatIncomingFile;
import ru.biblio.web.service.conversation.FormatIncomingFileFabrica;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class ConversationServiceImpl implements ConversationService {

    @Value("${conversation.text.format}")
    private String fileType;

    @Autowired
    private ServletContext servletContext;

    @Override
    public byte[] convert(MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        File directoryTemp =    (File) servletContext.getAttribute(ServletContext.TEMPDIR);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        FormatIncomingFileFabrica fabrica = new FormatIncomingFileFabrica();
        FormatIncomingFile formatter = fabrica.converter(file.getContentType(),fileType);
        formatter.conversation(file,directoryTemp);
        Future<byte[]> transformationText = executorService.submit(formatter);
        return transformationText.get();
    }
}
