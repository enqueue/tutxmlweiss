/*
 * Copyright (c) 2021 Riege Software. All rights reserved.
 * Use is subject to license terms.
 */
package com.riege.tutorial.xmlweiss;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

final class XMLTransformator extends AbstractXMLWeissThing {

    static String transform(String xml) throws Exception {
        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        fac.setSchema(getSchema());
        fac.setNamespaceAware(true);
        DocumentBuilder bob = fac.newDocumentBuilder();
        Document doc = bob.parse(new InputSource(new StringReader(xml)));
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer(new StreamSource(XMLTransformator.class.getResourceAsStream("xslt/stylesheet.xslt")));
        try (StringWriter sw = new StringWriter()) {
            t.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        }
    }

}
