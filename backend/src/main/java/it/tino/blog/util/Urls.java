package it.tino.blog.util;

public class Urls {

    public static String makeStringUrlCompatible(String string) {
        return string.toLowerCase()
                .replaceAll("[^\\w\\s.-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-")
                .replaceAll("^-+|-+$", "");
    }
}
