package ru.szhernovoy.generator.service;/**
 * Created by szhernovoy on 21.02.2017.
 */

import java.io.File;

/**
 * Класс проверяет правильность введенных аргументов
 * и возвращает название аргумента из списка
 */
public class Validator {

    /**аргументы программы */
    private String[] args;

    public Validator(String[] args) {
        this.args = args;
    }

    /**
     * Основной метод проверки правильности аргументов
     * @return
     */
    public boolean matchFiles(){
        boolean result = false;
        if(args.length == 3){
            if(matchArgument(args[0],"xml", false)
                    && matchArgument(args[1],"tsv",false)
                    && matchArgument(args[2],"txt",true)){
                System.out.println("This arguments is OK!");
                result = true;
            } else {
                throw new MyException("Incorrect argument");
            }
        }
        return result;
    }

    /**
     * Проверяет аргумент на соответствие
     * @param argument
     * @param fileExtension
     * @param newFile
     * @return
     */
    private boolean matchArgument(String argument, String fileExtension, boolean newFile){
        boolean result = false;
        if(argument.endsWith(fileExtension)){
            File file = new File(argument);
            if(!newFile){
                if(file.exists() && file.canRead() && file.isFile() && file.length() > 0){
                    result = true;
                }
            } else {
                   if(argument != null){
                       result = true;
                   }
            }
        }
        return  result;
    }

    /**
     * Получить аргумент по номеру из списка
     * @param num
     * @return
     */
    public String getArgument(int num){
        return this.args[num];
    }


}
