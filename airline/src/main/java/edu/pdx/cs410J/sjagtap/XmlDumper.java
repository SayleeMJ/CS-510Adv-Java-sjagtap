package edu.pdx.cs410J.sjagtap;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

import java.io.IOException;
import java.io.Writer;

public class XmlDumper implements AirlineDumper {

    private final Writer writer;

    /**
     * Constructor for TextDumper.
     *
     * @param writer object pointing to destination of data.
     */
    public XmlDumper(Writer writer) {
        this.writer = writer;
    }
    @Override
    public void dump(AbstractAirline airline) throws IOException {

    }
}
