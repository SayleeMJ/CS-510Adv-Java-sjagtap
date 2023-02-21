package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ConverterTest {

    @Test
    void checkingIfFileIsExist(){
        File textFile = new File("src/test/resources/edu/pdx/cs410J/sjagtap/testingTextFile1.txt");
        File xmlFile = new File("src/test/resources/edu/pdx/cs410J/sjagtap/testingXmlFile.xml");
        Converter.converter(textFile, xmlFile);
        assertThat("Please provide correct and existing file name", equalTo("Please provide correct and existing file name"));
    }

    @Test
    void checkingIfFileConvertsToXmlFile(){
        File textFile = new File("src/test/resources/edu/pdx/cs410J/sjagtap/testingTextFile.txt");
        File xmlFile = new File("src/test/resources/edu/pdx/cs410J/sjagtap/testingXmlFile.xml");
        Converter.converter(textFile, xmlFile);
    }

    @Test
    void checkingIfFileEmpty(){
            File textFile = new File("src/test/resources/edu/pdx/cs410J/sjagtap/testingFileForConverter.txt");
            File xmlFile = new File("src/test/resources/edu/pdx/cs410J/sjagtap/testingXmlFile.xml");
            Converter.converter(textFile, xmlFile);
            assertThat("Text file is empty. Please add flight details first. ", equalTo("Text file is empty. Please add flight details first. "));


    }

    @Test
    void creatingNewXMLFileIfNotPresent(){
        File textFile = new File("src/test/resources/edu/pdx/cs410J/sjagtap/testingTextFile.txt");
        File xmlFile = new File("src/test/resources/edu/pdx/cs410J/sjagtap/testingXmlFileNew.xml");
        Converter.converter(textFile, xmlFile);
    }

}
