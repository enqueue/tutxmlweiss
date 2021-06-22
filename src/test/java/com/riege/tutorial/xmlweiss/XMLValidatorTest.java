/*
 * Copyright (c) 2021 Riege Software. All rights reserved.
 * Use is subject to license terms.
 */
package com.riege.tutorial.xmlweiss;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link XMLValidator}
 * @author Felix Mueller
 */
class XMLValidatorTest extends AbstractXMLWeissTestCase {

    @Test
    void exampleIsValid() throws Exception {
        XMLValidator.validate(loadExample());
    }

    @Test
    void stringTakesAnything() throws Exception {
        assertXMLValid("bar", WURST);
    }

    @Test
    void normalizedStringMakesAllGood() throws Exception {
        assertXMLValid("baz", WURST);
    }

    @Test
    void tokenMakesAllGood() throws Exception {
        assertXMLValid("qux", WURST);
    }

    @Test
    void nmTokenIsLessForgiving() throws Exception {
        assertXMLInvalid("quux", WURST);
    }

    private static void assertXMLValid(String elementName, String input)
        throws Exception
    {
        assertTrue(XMLValidator.validate(buildXML(elementName, input)));
    }

    private static void assertXMLInvalid(String elementName, String input)
        throws Exception
    {
        assertFalse(XMLValidator.validate(buildXML(elementName, input)));
    }

    private static String buildXML(String elementName, String input)
        throws Exception
    {
        return loadExample().replaceAll(
            "<" + elementName + ">.*</" + elementName + ">",
            "<" + elementName + ">" + input + "</" + elementName + ">");
    }

}
