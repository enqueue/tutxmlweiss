/*
 * Copyright (c) 2021 Riege Software. All rights reserved.
 * Use is subject to license terms.
 */
package com.riege.tutorial.xmlweiss;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Abstract base class for tests in our great XML whitespace tutorial
 * @author Felix Mueller
 */
abstract class AbstractXMLWeissTestCase {

    protected static final String WURST =
        "  Salami\n Mortadella \t\t \r\n Mettwurst ";

    protected static final String DAUERWURST =
        "  Salami\n Mortadella \t\t \n Mettwurst ";

    protected static final String loadExample() throws Exception {
        return Files.readString(
            Paths.get(XMLDOMParserTest.class.getResource("xml/example.xml").toURI()));
    }

    protected static final String loadBrokenExample() throws Exception {
        return Files.readString(
            Paths.get(XMLDOMParserTest.class.getResource("xml/example_broken.xml").toURI()));
    }

}
