package edu.pdx.cs410J.sjagtap;

import org.junit.jupiter.api.Test;

public class Project5Test {

    /**
     * Validate -README
     */
    @Test
    void validateMain() {
        String[] args = new String[] {
                "-README",
        };
        Project5.main(args);
    }

    /**
     * Validate is command line arguments are missing .
     */
    @Test
    void validateMainNoArgs() {
        String[] args = new String[0];
        Project5.main(args);
    }


    /**
     * Validate airline -print
     */
    @Test
    void validateMainPrint() {
        String[] args = new String[] {
                "-host",
                "localhost",
                "-port",
                "8080",
                "-print",
                "AirDave",
                "123",
                "PDX",
                "07/19/2023",
                "1:02",
                "pm",
                "ORD",
                "07/19/2023",
                "6:22",
                "pm"
        };
        Project5.main(args);
    }

    @Test
    void validateMainSearch() {
        String[] args = new String[] {
                "-host",
                "localhost",
                "-port",
                "8080",
                "-search",
                "AirDave"
        };
        Project5.main(args);
    }

    @Test
    void validateMainSearchWithSpecifiedDESTAndSRC() {
        String[] args = new String[] {
                "-host",
                "localhost",
                "-port",
                "8080",
                "-search",
                "AirDave",
                "PDX",
                "ORD"
        };
        Project5.main(args);
    }
}
