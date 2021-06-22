/*
 * Copyright (c) 2021 Riege Software. All rights reserved.
 * Use is subject to license terms.
 */
package com.riege.tutorial.xmlweiss;

import org.junit.jupiter.api.Test;

public class XMLTransformatorTest extends AbstractXMLWeissTestCase {

    @Test
    void simpleTransormation() throws Exception {
        String s = XMLTransformator.transform(loadExample());
        System.out.println(s);
    }

}
