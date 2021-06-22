/*
 * Copyright (c) 2021 Riege Software. All rights reserved.
 * Use is subject to license terms.
 */
package com.riege.tutorial.xmlweiss;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

/**
 * Base class for XML whitespace tutorial
 */
abstract class AbstractXMLWeissThing {

    private static Schema schema;

    protected static final Schema getSchema() {
        if (schema == null) {
            try {
                schema = SchemaFactory.newDefaultInstance()
                    .newSchema(new StreamSource(
                        AbstractXMLWeissThing.class
                            .getResourceAsStream("schema/schema.xsd")));
            } catch (SAXException saxe) {
                throw new RuntimeException(
                    "Error loading schema", saxe);
            }
        }
        return schema;
    }

}
