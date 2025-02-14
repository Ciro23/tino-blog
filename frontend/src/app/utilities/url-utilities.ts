/**
 * Converts a string into a URL-friendly format.
 *
 * Examples:
 * - `"HELLO WORLD"` → `"hello-world"`
 * - `" hello world "` → `"hello-world"`
 * - `"hello--------world.......123"` → `"hello-world-123"`
 * - `"hello world\|!"£$%&/()=?^ìèé+*òçà°ù§,;.:-_<>@#"` → `"hello-world"`
 * - `"hello world 123 1.0.0"` → `"hello-world-123-1-0-0"`
 *
 * @param input The string to make URL safe.
 * @returns A human-readable string to use in URLs. Useful for creating URL slugs.
 */
export function makeStringUrlCompatible(input: string): string {
  return input.toLowerCase()
    .replace(/[^a-z0-9\s\-.]/gi, '') // Remove all characters except letters, numbers, spaces, dots, and hyphens
    .replace(/\s+/g, '-') // Replace one or more whitespace characters with a single hyphen
    .replace(/\.+/g, '-') // Replace one or more dots with a single hyphen
    .replace(/-+/g, '-') // Replace multiple consecutive hyphens with a single hyphen
    .replace(/^-+|-+$/g, ''); // Remove trailing hyphens
}
