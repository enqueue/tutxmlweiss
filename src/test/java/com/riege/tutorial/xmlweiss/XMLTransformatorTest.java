/*
 * Copyright (c) 2021 Riege Software. All rights reserved.
 * Use is subject to license terms.
 */
package com.riege.tutorial.xmlweiss;

import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class XMLTransformatorTest extends AbstractXMLWeissTestCase {

    @Test
    void simpleTransormation() throws Exception {
        String s = XMLTransformator.transform(loadExample());
        try (OutputStreamWriter ow =
            new OutputStreamWriter(System.out, StandardCharsets.UTF_8))
        {
            ow.write(s);
            ow.flush();
        }
    }

}
