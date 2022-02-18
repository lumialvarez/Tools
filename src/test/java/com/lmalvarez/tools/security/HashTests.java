package com.lmalvarez.tools.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HashTests {

    @Test
    public void md5() {
        String hash = Hash.MD5("1234567890");

        assertEquals(hash,"e807f1fcf82d132f9bb018ca6738a19f");
    }

    @Test
    public void sha1() {
        String hash = Hash.SHA1("1234567890");

        assertEquals(hash,"01b307acba4f54f55aafc33bb06bbbf6ca803e9a");
    }

    @Test
    public void sha256() {
        String hash = Hash.SHA256("1234567890");

        assertEquals(hash,"c775e7b757ede630cd0aa1113bd102661ab38829ca52a6422ab782862f268646");
    }

    @Test
    public void sha384() {
        String hash = Hash.SHA384("1234567890");

        assertEquals(hash,"ed845f8b4f2a6d5da86a3bec90352d916d6a66e3420d720e16439adf238f129182c8c64fc4ec8c1e6506bc2b4888baf9");
    }

    @Test
    public void sha512() {
        String hash = Hash.SHA512("1234567890");

        assertEquals(hash,"12b03226a6d8be9c6e8cd5e55dc6c7920caaa39df14aab92d5e3ea9340d1c8a4d3d0b8e4314f1f6ef131ba4bf1ceb9186ab87c801af0d5c95b1befb8cedae2b9");
    }

}
