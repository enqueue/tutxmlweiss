/*
 * Copyright (c) 2021 Riege Software. All rights reserved.
 * Use is subject to license terms.
 */
package com.riege.tutorial.xmlweiss;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

/**
 * Class for validating XML documents against our favourite XML schema
 * @author Felix Mueller
 */
final class XMLValidator extends AbstractXMLWeissThing {

    static boolean validate(String xml) throws Exception {
        try {
            Validator v = getSchema().newValidator();
            v.setErrorHandler(new MyErrorHandler());
            v.validate(
                new StreamSource(new StringReader(xml)));
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
