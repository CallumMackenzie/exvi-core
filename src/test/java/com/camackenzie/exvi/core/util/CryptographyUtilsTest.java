/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author callum
 */
public class CryptographyUtilsTest {

    public CryptographyUtilsTest() {
    }

    @Test
    public void testEncodeDecodeString() {
        String in = "ABCDEFGHIJKLMNOPQRSTUVWXYZdnusnda78dh2nd289ddn2'[d/[[d/ds/dsdi";
        String encoded = CryptographyUtils.encodeString(in);
        String decoded = CryptographyUtils.decodeString(encoded);
        assertEquals(in, decoded);
    }

}
