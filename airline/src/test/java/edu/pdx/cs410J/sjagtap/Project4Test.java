package edu.pdx.cs410J.sjagtap;

import org.junit.jupiter.api.Test;

public class Project4Test {
    /**
     * Validate airline -xmlFile xmlFileName
     */
    @Test
    void validateMainForXml() {
        String[] args = new String[] {
                "-xmlFile",
                "testingXML.xml",
                "Indigo",
                "70",
                "SCL",
                "10/10/2023",
                "1:30",
                "am",
                "SNN",
                "10/10/2023",
                "2:30",
                "am"
        };
        Project3.main(args);
    }
}
