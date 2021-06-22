/*
 * Copyright (c) 2021 Riege Software. All rights reserved.
 * Use is subject to license terms.
 */
package com.riege.tutorial.xmlweiss;

import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link XMLDOMParser}
 * @author Felix Mueller
 */
class XMLDOMParserTest extends AbstractXMLWeissTestCase {

    @Test
    void parseNoSchemaBar() throws Exception {
        assertBar(
           DAUERWURST,
           WURST,
           XMLDOMParser::parseNoSchema);
    }

    @Test
    void parseNoSchemaBarPreserveCRLF() throws Exception {
        assertBar(
           "  Salami\n Mortadella \t\t \r\n Mettwurst ",
           "  Salami\n Mortadella \t\t &#xD;&#xA; Mettwurst ",
           XMLDOMParser::parseNoSchema);
    }

    @Test
    void parseNoSchemaBaz() throws Exception {
        assertBaz(
            DAUERWURST,
            WURST,
            XMLDOMParser::parseNoSchema);
    }

    @Test
    void parseNoSchemaQux() throws Exception {
        assertQux(
            DAUERWURST,
            WURST,
            XMLDOMParser::parseNoSchema);
    }

    @Test
    void parseNoSchemaQuux() throws Exception {
        assertQuux(
            DAUERWURST,
            WURST,
            XMLDOMParser::parseUsingSchema);
    }

    @Test
    void parseSchemaBar() throws Exception {
        assertBar(
            DAUERWURST,
            WURST,
            XMLDOMParser::parseUsingSchema);
    }

    @Test
    void parseSchemaBaz() throws Exception {
        assertBaz(
            DAUERWURST,
            WURST,
            XMLDOMParser::parseUsingSchema);
    }

    @Test
    void parseSchemaQux() throws Exception {
        assertQux(
            DAUERWURST,
            WURST,
            XMLDOMParser::parseUsingSchema);
    }

    @Test
    void parseSchemaQuux() throws Exception {
        assertQuux(
            DAUERWURST,
            WURST,
            XMLDOMParser::parseUsingSchema);
    }

    @Test
    void parseSchemaNSBar() throws Exception {
        assertBar(
           DAUERWURST,
           WURST,
           XMLDOMParser::parseUsingSchemaNSAware);
    }

    @Test
    void parseSchemaNSBaz() throws Exception {
        assertBaz(
            "  Salami  Mortadella      Mettwurst ",
            WURST,
            XMLDOMParser::parseUsingSchemaNSAware);
    }

    @Test
    void parseSchemaNSQux() throws Exception {
        assertQux(
            "Salami Mortadella Mettwurst",
            WURST,
            XMLDOMParser::parseUsingSchemaNSAware);
    }

    @Test
    void parseSchemaNSQuux() throws Exception {
        assertQuux(
            "Salami Mortadella Mettwurst", // not valid
            WURST,
            XMLDOMParser::parseUsingSchemaNSAware);
    }

    @Test
    void parseAttributeNormalization() throws Exception {
        String s = "<foo bar='\tbaz\r\nqux  '/>";
        Document doc = XMLDOMParser.parseNoSchema(s);
        String attValue = doc
            .getElementsByTagName("foo")
            .item(0)
            .getAttributes()
            .getNamedItem("bar")
            .getNodeValue();
        // no collapsing of leading, trailing whitespace if type is CDATA
        assertEquals(" baz qux  ", attValue);
    }

    @Test
    void parseCarriageReturnNaive() throws Exception {
        String s = "<foo>wurst und\r\nkäse</foo>";
        Document doc = XMLDOMParser.parseNoSchema(s);
        String nodeValue = doc
            .getElementsByTagName("foo")
            .item(0)
            .getFirstChild()
            .getNodeValue();
        assertEquals("wurst und\nkäse", nodeValue);
    }

    @Test
    void parseCarriageReturnCharacterReference() throws Exception {
        String s = "<foo>wurst und&#xD;&#xA;käse</foo>";
        Document doc = XMLDOMParser.parseNoSchema(s);
        String nodeValue = doc
            .getElementsByTagName("foo")
            .item(0)
            .getFirstChild()
            .getNodeValue();
        assertEquals("wurst und\r\nkäse", nodeValue);
    }

    @Test
    void parseInvalid() throws Exception {
        assertThrows(
            RuntimeException.class,
            () -> XMLDOMParser.parseUsingSchemaNSAwareValidating(
                loadBrokenExample()));
    }

    private static void assertBar(String expected, String input,
        Function<String, Document> parserFunction) throws Exception
    {
        assertElement("bar", expected, input, parserFunction);
    }

    private static void assertBaz(String expected, String input,
        Function<String, Document> parserFunction) throws Exception
    {
        assertElement("baz", expected, input, parserFunction);
    }

    private static void assertQux(String expected, String input,
        Function<String, Document> parserFunction) throws Exception
    {
        assertElement("qux", expected, input, parserFunction);
    }

    private static void assertQuux(String expected, String input,
        Function<String, Document> parserFunction) throws Exception
    {
        assertElement("quux", expected, input, parserFunction);
    }

    private static void assertElement(String elementName, String expected,
        String input, Function<String, Document> parserFunction) throws Exception
    {
        Document doc = parserFunction.apply(
            loadExample().replaceAll(
                "<" + elementName + ">.*</" + elementName + ">",
                "<" + elementName + ">" + input + "</" + elementName + ">"));
        printNode(doc, "");
        String actual = doc.getElementsByTagName(elementName)
            .item(0).getFirstChild().getNodeValue();
        assertEquals(expected, actual);
    }

    private static void printNode(Node rootNode, String indent) {
        System.out.println(indent + "["
            + translateNodeType(rootNode.getNodeType()) + "] "
            + rootNode.getNodeName() + ": "
            + rootNode.getNodeValue());
        NodeList nl = rootNode.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            printNode(nl.item(i), indent + "   ");
        }
    }

    private static String translateNodeType(int type) {
        switch (type) {
            case Node.ELEMENT_NODE:
                return "element";
            case Node.ATTRIBUTE_NODE:
                return "attribute";
            case Node.TEXT_NODE:
                return "text";
            case Node.CDATA_SECTION_NODE:
                return "cdata";
            case Node.ENTITY_REFERENCE_NODE:
                return "entity reference";
            case Node.ENTITY_NODE:
                return "entity";
            case Node.PROCESSING_INSTRUCTION_NODE:
                return "processing instruction";
            case Node.COMMENT_NODE:
                return "comment";
            case Node.DOCUMENT_NODE:
                return "document";
            case Node.DOCUMENT_TYPE_NODE:
                return "document type";
            case Node.DOCUMENT_FRAGMENT_NODE:
                return "document fragment";
            case Node.NOTATION_NODE:
                return "notation";
            default:
                throw new IllegalArgumentException(
                    "unknown node type " + type);
        }
    }

}
