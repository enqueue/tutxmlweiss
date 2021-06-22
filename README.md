# Notes on XML Whitespace Handling

When working with XML documents in an EDI context, or when designing an XML schema, it is useful to be familiar with the way whitespace is treated in commonly used XML technologies and by the [JAXP](https://docs.oracle.com/javase/tutorial/jaxp/index.html) implementation. This short tutorial can help you discover these behaviors

## XML Well-Formedness

The Extensible Markup Language (XML) itself is mostly concerned with the _well-formedness_ of an XML document, as opposed to its _validity_ which can be tested by other means. The specification allows us to use almost any whitespace character, in particular tab, line feed or carriage return. Names may not contain any whitespace.

You may indicate to the processor that white space should be treated "as is" using the `xml:space="preserve"` attribute, but I never saw this in the wild.

Line endings are [normalized](https://www.w3.org/TR/REC-xml/#sec-line-ends) when an XML document is processed, so that CR or CR+LF are treated as a single LF.

Tab or line feed characters in attribute values are [normalized](https://www.w3.org/TR/REC-xml/#AVNormalize) to a single space character by the processor.

## XML Schema

The XML specification itself already provides some _validity_ constraints that can be used in a Document Type Definition (DTD), but we use the power of [XML schema](https://www.w3.org/TR/2004/REC-xmlschema-1).

Here are the most popular candidates:

Type | Description
---- | -----------
[string](https://www.w3.org/TR/xmlschema-2/#string) | Primitive built-in datatype for all XML character data
[normalizedString](https://www.w3.org/TR/xmlschema-2/#normalizedString) | String without CR, LF or tab
[token](https://www.w3.org/TR/xmlschema-2/#token) | String without CR, LF, or tab. No leading or trailing space characters, no consecutive space characters
[NMTOKEN](https://www.w3.org/TR/xmlschema-2/#NMTOKEN) | Letters, digits, dash, underscore, colon

Mixed content character data is processed as string.

## JAXP DOM Parser

We look at the DOM parser, but there are similar mechanisms for SAX or STaX parsers.

We need to make the parser aware of the content model, i.e. we need to supply an XML schema. Else the parser will not be able to handle whitespace as we expect it to.

Let's take a look at how a parser is created via [DocumentBuilderFactory](https://devdocs.io/openjdk~11/java.xml/javax/xml/parsers/documentbuilderfactory):

```
DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
DocumentBuilder bob = fac.newDocumentBuilder();
bob.parse(new InputSource(new StringReader(xml)));
```

This is a very simple way to create a DOM parser that is _not_ aware of the document's content model. Let's supply our schema:

```
fac.setSchema(getSchema());
fac.setNamespaceAware(true);
```

This will make our parser behave according to the standard behavior to be expected from a content-model aware XML processor.

To keep the DOM smaller, we can ask the parser not to create unneccessary text nodes for ignorable whitespace. This is useful when we work with "pretty-printed" XML documents.

```
fac.setIgnoringElementContentWhitespace(true);
```

There are some implementation ([Xerces-J](https://xerces.apache.org/xerces-j/features.html)) specific toggles we could use, but let's stick to the API for now.

## JAXP Validation

We can either validate during parsing or use a [Validator](https://devdocs.io/openjdk~11/java.xml/javax/xml/validation/validator).

Validation is automatically enabled when we supply a schema, but we have to supply an [ErrorHandler](https://devdocs.io/openjdk~11/java.xml/org/xml/sax/errorhandler) to treat any validation error as an Exception:

```
fac.setSchema(getSchema());
fac.setNamespaceAware(true);
...
DocumentBuilder bob = fac.newDocumentBuilder();
bob.setErrorHandler(new MyErrorHandler());
```

We can also create a stand-alone [Validator](https://devdocs.io/openjdk~11/java.xml/javax/xml/validation/validator) and validate, again using our custom [ErrorHandler](https://devdocs.io/openjdk~11/java.xml/org/xml/sax/errorhandler):

```
Validator v = getSchema().newValidator();
v.setErrorHandler(new MyErrorHandler());
v.validate(new StreamSource(new StringReader(xml)));
```

## Links

* [javax.xml](https://devdocs.io/openjdk~11/java.xml/module-summary)
* [Schema Instance Schema](https://www.w3.org/2001/XMLSchema.xsd)
* [XML Schema Part 2: Datatypes Second Edition](https://www.w3.org/TR/xmlschema-2)
* [XML Schema Part 1: Structures Second Edition, Schema Component Details](https://www.w3.org/TR/2004/REC-xmlschema-1-20041028/structures.html#components)
* [XML 1.0 Specification](https://www.w3.org/TR/REC-xml/)