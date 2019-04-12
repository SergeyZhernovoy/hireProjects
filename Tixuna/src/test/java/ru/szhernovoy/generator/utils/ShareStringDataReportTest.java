package ru.szhernovoy.generator.utils;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Admin on 24.02.2017.
 */
public class ShareStringDataReportTest {

    @Test
    public void whenGiveComplexStringWeGetArray(){
        int width = 7;
        String result;

        List<String> listData = new ArrayList<>();
        String source  = "Ким Чен Ир Бухенвальдович";
        String arrWords[] = source.split(" ");
        List<String> resultWords = new LinkedList<>();

        for(String string : arrWords){
            if(string.length() <= width){
                resultWords.add(string);
            } else {

                char[] chars = string.toCharArray();
                int current = width;
                for(int index = 0; index < chars.length;){

                    String chunk = new String(chars,index,current);
                    resultWords.add(chunk);
                    index+=current;

                    if(index+current > string.length()){
                        current = string.length() - index;
                    }

                }
            }
        }


        arrWords = resultWords.toArray(new String[resultWords.size()]);
        ArrayList<String> arrPhrases = new ArrayList<>(); // Коллекция подстрок(фраз)
        StringBuilder stringBuffer = new StringBuilder(); // Буфер для накопления фразы
        int cnt = 0;   // Счётчик, чтобы не выйти за пределы 30 символов
        int index = 0; // Индекс элемента в массиве arrWords. Сразу указывает на первый элемент
        int length = arrWords.length; // Общее количество слов (длина массива)

        while (index != length) {  // Пока не дойдём до последнего элемента

            if (cnt + arrWords[index].length() <= width) { // Если текущая фраза + текущее слово в массиве arrWords не превышает 30
                cnt += arrWords[index].length() + 1;  // То увеличиваем счётчик
                stringBuffer.append(arrWords[index]).append(" ");  // и накапливаем фразу
                index++;   // Переходим на следующее слово
            }


            else {   // Фраза превысит лимит в 30 символов

                if(arrWords[index].length() > width){

                }


                arrPhrases.add(stringBuffer.toString());   // Добавляем фразу в коллекцию
                stringBuffer = new StringBuilder();
                cnt = 0;                                   // Обнуляем счётчик
            }

        }

        if (stringBuffer.length() > 0) {
            arrPhrases.add(stringBuffer.toString());       // Забираем "остатки"
        }



          System.out.println(arrPhrases.toString());
        }


}