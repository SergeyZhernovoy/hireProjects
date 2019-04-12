package ru.biblio.web.service.conversation;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.UUID;

public class FormatIncomingFileToHtml implements FormatIncomingFile {

    private MultipartFile file;
    private File directoryTemp;

    public FormatIncomingFileToHtml() {
    }

    @Override
    public void conversation(MultipartFile file, final File temp) throws IOException {
        this.file = file;
        this.directoryTemp = temp;

    }

    @Override
    public byte[] call() throws Exception {
        File outFile = File.createTempFile(UUID.randomUUID().toString(), ".tmp", directoryTemp);
        PDDocument pdf = PDDocument.load(file.getInputStream());
        PDFDomTree parser = null;
        try {
            parser = new PDFDomTree();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Writer output = new PrintWriter(outFile.getAbsolutePath(), "utf-8");
        parser.writeText(pdf, output);
        output.close();
        if (pdf != null) {
            pdf.close();
        }
        byte[] outByte = null;
        try(ByteArrayOutputStream out = new ByteArrayOutputStream();InputStream input = new BufferedInputStream(new FileInputStream(outFile)) ){
            int data = 0;
            while ((data = input.read()) != -1){
                out.write(data);
            }
            outByte = out.toByteArray();
        }
        File forDelete = new File(outFile.getAbsolutePath());
        forDelete.delete();
        return outByte;
    }
}
