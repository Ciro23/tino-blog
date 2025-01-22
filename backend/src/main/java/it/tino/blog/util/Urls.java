package it.tino.blog.util;

public class Urls {

    public static String makeStringUrlCompatible(String string) {
        return string.toLowerCase()
                .replaceAll("[^\\w\\s-]", "") // Remove all characters except letters, numbers, spaces, and hyphens
                .replaceAll("\\s+", "-") // Replace one or more whitespace characters with a single hyphen
                .replaceAll("-+", "-") // Replace multiple consecutive hyphens with a single hyphen
                .replaceAll("^-+|-+$", ""); // Remove trailing hyphens
    }
}
