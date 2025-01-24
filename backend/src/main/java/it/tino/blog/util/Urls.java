package it.tino.blog.util;

public class Urls {

    /**
     * E.g.
     * <ul>
     *     <li>"HELLO WORLD" => "hello-world"</li>
     *     <li>" hello world " => "hello-world"</li>
     *     <li>"hello--------world.......123" => "hello-world-123"</li>
     *     <li>"hello world\|!"£$%&/()=?^ìèé+*òçà°ù§,;.:-_<>@#" => "hello-world"</li>
     *     <li>"hello world 123 1.0.0" => "hello-world-123-1-0-0"</li>
     * </ul>
     * @param string The string to make URL safe.
     * @return A human-readable string to use in URLs. Useful to create URL slugs.
     */
    public static String makeStringUrlCompatible(String string) {
        return string.toLowerCase()
                .replaceAll("[^a-zA-Z0-9\\s\\-.]", "") // Remove all characters except letters, numbers, spaces, dots, and hyphens
                .replaceAll("\\s+", "-") // Replace one or more whitespace characters with a single hyphen
                .replaceAll("\\.+", "-") // Replace one or more dots with a single hyphen
                .replaceAll("-+", "-") // Replace multiple consecutive hyphens with a single hyphen
                .replaceAll("^-+|-+$", ""); // Remove trailing hyphens
    }
}
