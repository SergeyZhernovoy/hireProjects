package ru.biblio.web.service.conversation;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


public class FormatIncomingFileFabrica {

    public FormatIncomingFile converter(String typeFrom,String typeTo){
        FormatIncomingFile format = null;

        if(typeFrom.contains("application/")){
            typeFrom = typeFrom.substring("application/".length());
        }

        String result = String.format("%s->%s",typeFrom,typeTo);
        switch (result){
            case "pdf->html":
                format = new FormatIncomingFileToHtml();
                break;
        }
        return format;
    };

}
