FROM postgres:17

# Initializes the database.
# The prefix "01", "02" (etc.) is necessary because they're executed in alphabetical order!
COPY schema.sql /docker-entrypoint-initdb.d/01-schema.sql
COPY data.sql /docker-entrypoint-initdb.d/02-data.sql
