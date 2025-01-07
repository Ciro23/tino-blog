## Introduction

This is my blog, built for my own needs.

## Features

- Non-authenticated users are only able to:
    - Read the published articles.
    - Read the articles from a bunch of RSS feeds I find useful, mostly programming related.
- Authenticated users are also able to:
    - Manage articles (publish, edit and delete).
    - Manage the followed RSS feeds.
    - Force the reload of the RSS feeds cache.

The blog articles support Markdown and code syntax highlighting.

> NOTE: to correctly display the RSS articles, HTML sanitization is bypassed (only for this case), so that `<iframe>`
> elements are displayed as intended.  
> This means that only trusted RSS feeds should be followed, as no HTML sanitization means XSS!

### RSS feeds caching

The articles fetched from the followed RSS feeds are cached for a few hours to drastically improve loading times and to
reduce the server's workload... bandwidth ain't free.

## Compiling from source

### Using Docker

> Recommended for production.

#### Requirements

- Docker
- Docker Compose

#### Steps

1. Navigate to the repository root, copy the file `.env.example`, rename it to `.env` and set your sensitive data.
2. Generate the private and public keys used to create the login tokens. Please refer
   to `backend/src/main/resources/certs/README.md` for instructions.
3. Run the application:
   ```shell
   docker compose up -d --build
   ```
4. Change the password (along the other details) of the default user:
   ```shell
   docker exec -it tino-blog-db bash
   psql -d tino_blog -U postgres
   ```
   Enter your password, then execute the update statement:
   ```postgresql
   UPDATE users
   SET username = 'your_username',
       email = 'your@email.org',
       password = 'your_super_secret_bcrypt_encrypted_password'
   WHERE id = '4c7dbc23-b524-4dd2-95f0-c0cb974588c7';
   ```

### Manual native setup (for local development only!)

#### Requirements

- Java 21
- PostgreSQL 17 (15 and 16 are also both fine as well)
- Angular CLI

#### Steps

> Remember that after modifying the files `*.properties`, it may be necessary to run:
> ```shell
> mvn clean package
> ```

1. Configure the Spring "active profile" to `dev`, so that `application-dev.properties` is used over
   `application.properties`: this allows Spring to generate the database tables and to use default certificates for
   user authentication.
2. The database must be created manually using PostgreSQL: the default name is "tino_blog", but it can be customized
   using environment variables (see point number 3): the tables will be created by Spring during the first run.
3. To initialize the database with the default data, the property `spring.sql.init.mode`,
   inside `application-dev.properties` must be set to `always` (change it back after the first initialization, or it will
   execute every time the application is run).
   > The first admin user is created automatically:  
   > **Email**: admin@test.org  
   > **Password**: password
4. Configure the following environment variables for the backend (it can be done using IntelliJ IDEA "run configuration"
   or via system, for example, by running `export MY_ENV_VAR=123` on Linux and macOS):
    - DB_HOST (default "localhost")
    - DB_PORT (default "5432")
    - DB_NAME (default "tino_blog")
    - DB_USER
    - DB_PASSWORD
5. Navigate to the `backend` directory and compile it:
   ```shell
   mvn clean package
   ```
6. Run the backend:
   ```shell
   java -jar target/tino-blog-1.0.0.war
   ```
7. Navigate to the `frontend` directory and run it:
   ```shell
   npm install
   ng serve
   ```

---

### In case of database changes

If the database schema changes, the DDL instructions must be exported into the file `backend/schema.sql`, which is used
to build the Docker image for the database. This can be done with:

```shell
pg_dump -U db_username tino_blog >> schema.sql
```

### Customize Bootstrap theme

Some Bootstrap colors were changed in the file `frontend/src/custom_bootstrap/custom.scss`.  
If the SASS changes, it needs to be compiled again in CSS:

1. Install SASS:
   ```shell
   npm install -g sass
   ```
2. Compile the `.scss` file to `.css`:
   ```shell
   sass --watch frontend/src/custom_bootstrap/custom.scss frontend/src/custom_bootstrap/custom.css
   ```

The full reference can be found [here](https://getbootstrap.com/docs/5.3/customize/sass/).

---

## Colors...

Yes, I did steal some colors from the color palette of GitHub, as it's perfect, and I'm no good designer.
