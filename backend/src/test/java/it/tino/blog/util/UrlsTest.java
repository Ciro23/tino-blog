package it.tino.blog.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UrlsTest {

    @Test
    public void testEmptyString() {
        String actual = Urls.makeStringUrlCompatible("");
        Assertions.assertEquals("", actual);
    }

    @Test
    public void shouldMakeTextLowercase() {
        String actual = Urls.makeStringUrlCompatible("UPPERCASE");
        Assertions.assertEquals("uppercase", actual);
    }

    @Test
    public void shouldJoinWordsWithHyphen() {
        String actual = Urls.makeStringUrlCompatible("word1 word2 word3");
        Assertions.assertEquals("word1-word2-word3", actual);
    }

    @Test
    public void shouldNotHaveTrailingHyphens() {
        String actual = Urls.makeStringUrlCompatible(" word1 word2 word3 ");
        Assertions.assertEquals("word1-word2-word3", actual);
    }

    @Test
    public void testConsecutiveSpaces() {
        String actual = Urls.makeStringUrlCompatible("word1    word2         word3");
        Assertions.assertEquals("word1-word2-word3", actual);
    }

    @Test
    public void testSpecialCharacters() {
        String actual = Urls.makeStringUrlCompatible("?*&!.()/%$£\"'éèòçà°ù§+,;:<>^");
        Assertions.assertEquals("", actual);
    }

    @Test
    public void shouldPreserveHyphensBetweenWords() {
        String actual = Urls.makeStringUrlCompatible("hello---world");
        Assertions.assertEquals("hello-world", actual);
    }

    @Test
    public void shouldDeleteTrailingHyphens() {
        String actual = Urls.makeStringUrlCompatible("----hello-world-");
        Assertions.assertEquals("hello-world", actual);
    }
}
