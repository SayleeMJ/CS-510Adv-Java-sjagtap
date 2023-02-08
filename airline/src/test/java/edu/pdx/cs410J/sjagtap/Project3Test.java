package edu.pdx.cs410J.sjagtap;

import org.junit.jupiter.api.Test;

/**
 * A unit test for code in the <code>Project3</code> class.
 */
public class Project3Test {
    /**
     * Validate airline name.
     */
    @Test
    void validateMain() {
        String[] args = new String[] {
                "-README",
        };
        Project3.main(args);
    }
}
