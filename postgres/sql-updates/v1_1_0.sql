ALTER TABLE public.articles
    ADD slug character varying(255) UNIQUE;

UPDATE public.articles
SET slug = LOWER(
        REGEXP_REPLACE(
                REGEXP_REPLACE(
                        REGEXP_REPLACE(
                                REGEXP_REPLACE(
                                        REGEXP_REPLACE(
                                                title,
                                                '[^a-zA-Z0-9\s.\-]', '', 'g' -- Remove special characters except hyphens and dots
                                        ),
                                        '\s+', '-', 'g' -- Replace spaces with hyphens
                                ),
                                '\.+', '-', 'g' -- Replace one or more dots with a single hyphen
                        ),
                        '-+', '-', 'g' -- Remove consecutive hyphens
                ),
                '^-+|-+$', '', 'g' -- Remove leading/trailing hyphens
        )
)
WHERE slug IS NULL;

ALTER TABLE public.articles
    ALTER COLUMN slug SET NOT NULL;
