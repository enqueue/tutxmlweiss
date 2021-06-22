/*
 * Copyright (c) 2021 Riege Software. All rights reserved.
 * Use is subject to license terms.
 */
package com.riege.tutorial.xmlweiss;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Simple {@link ErrorHandler}
 */
final class MyErrorHandler implements ErrorHandler {

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        Logger.getLogger(XMLValidator.class.getName())
            .log(Level.WARNING, "SAX Warning", exception);
    }

    @Override
    public void error(SAXParseException saxpe) throws SAXException {
        throw new SAXException("error", saxpe);
    }

    @Override
    public void fatalError(SAXParseException saxpe)
        throws SAXException
    {
        throw new SAXException("fatal error", saxpe);
    }

}
