package ru.szhernovoy.generator.service;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by szhernovoy on 21.02.2017.
 */
public class ValidatorTest {
    @Test
    public void whenWeGiveFilesInMathchThenShouldReturnTrueORFalse() throws Exception {
        String first = getClass().getClassLoader().getResource("settings.xml").getPath();
        String second =getClass().getClassLoader().getResource("source-data.tsv").getPath(); ;
        String third = "example-report.txt";
        Validator validator = new Validator(new String[]{first,second,third});
        Assert.assertThat(true,is(validator.matchFiles()));
    }

}