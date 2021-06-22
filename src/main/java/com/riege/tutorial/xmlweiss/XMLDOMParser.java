/*
 * Copyright (c) 2021 Riege Software. All rights reserved.
 * Use is subject to license terms.
 */
package com.riege.tutorial.xmlweiss;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Parse XML into DOM
 */
final class XMLDOMParser extends AbstractXMLWeissThing {

    static Document parseNoSchema(String xml) {
        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder bob = fac.newDocumentBuilder();
            return bob.parse(new InputSource(new StringReader(xml)));
        } catch (Exception e) {
            throw new RuntimeException(
                "Error parsing", e);
        }
    }

    static Document parseUsingSchema(String xml) {
        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        fac.setSchema(getSchema());
        try {
            DocumentBuilder bob = fac.newDocumentBuilder();
            return bob.parse(new InputSource(new StringReader(xml)));
        } catch (Exception e) {
            throw new RuntimeException(
                "Error parsing", e);
        }
    }

    static Document parseUsingSchemaNSAware(String xml) {
        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        fac.setSchema(getSchema());
        fac.setNamespaceAware(true);
        // fac.setIgnoringElementContentWhitespace(true);
        try {
            DocumentBuilder bob = fac.newDocumentBuilder();
            // bob.setErrorHandler(new MyErrorHandler());
            return bob.parse(new InputSource(new StringReader(xml)));
        } catch (Exception e) {
            throw new RuntimeException(
                "Error parsing", e);
        }
    }

    static Document parseUsingSchemaNSAwareValidating(String xml) {
        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        fac.setSchema(getSchema());
        fac.setNamespaceAware(true);
        // fac.setIgnoringElementContentWhitespace(true);
        try {
            DocumentBuilder bob = fac.newDocumentBuilder();
            bob.setErrorHandler(new MyErrorHandler());
            return bob.parse(new InputSource(new StringReader(xml)));
        } catch (Exception e) {
            throw new RuntimeException(
                "Error parsing", e);
        }
    }

}
