package edu.pdx.cs410J.sjagtap;

import org.junit.jupiter.api.Test;

/**
 * A unit test for code in the <code>Project3</code> class.
 */
public class Project3Test {
    /**
     * Validate airline -pretty -
     */
    @Test
    void validateMainPretty() {
        String[] args = new String[] {
                "-pretty",
                "-",
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
