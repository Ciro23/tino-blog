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

1. Generate SSL certificates for HTTPS and save the files inside a secure location on your server.

   > You can use [Cloudflare origin CA](https://developers.cloudflare.com/ssl/origin-configuration/origin-ca/)
   > to generate the files `origin.pem` and `private.pem`.

2. Navigate to the repository root, copy the file `.env.example`, rename it to `.env`, then set your sensitive data.
3. Generate the private and public keys used to create the login tokens. Please refer
   to `backend/src/main/resources/certs/README.md` for instructions.
4. Run the application:

   ```shell
   docker compose up -d --build
   ```

5. Change the password (along the other details) of the default user:

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

6. Open the webapp: <https://localhost>

### Manual native setup (for local development only!)

#### Requirements

- Java 21
- PostgreSQL 17 (15 and 16 are also both fine as well)
- Angular CLI

#### Steps

> Remember that after modifying the `*.properties` files, it may be necessary to re-run:
>
> ```shell
> mvn clean package
> ```

1. The database must be created manually using PostgreSQL: the default name is "tino_blog", but it can be customized
   using environment variables (see point number 3): the tables will be created by Spring during the first run.

2. Configure the following environment variables for the backend (it can be done using IntelliJ IDEA "run configuration"
   or via system, for example, by running `export MY_ENV_VAR=123` on Linux and macOS):

    - DB_HOST (default "localhost")
    - DB_PORT (default "5432")
    - DB_NAME (default "tino_blog")
    - DB_USER
    - DB_PASSWORD

3. Navigate to the `backend` directory and compile it:

   ```shell
   mvn clean package
   ```

4. Run the backend:

   ```shell
   java -jar -Dspring.profiles.active=dev target/tino-blog-1.3.0.war
   ```

5. To initialize the database with default data (first user and a bunch of RSS feeds), you can run this:

   ```postgres
   psql -U postgres -d tino_blog -f src/main/resources/data.sql
   ```

   > Credentials of the first admin user, created during data initialization:  
   > **Email**: <admin@test.org>  
   > **Password**: password

6. Navigate to the `frontend` directory and run it:

   ```shell
   npm install
   ng serve
   ```

7. Open the webapp: <http://localhost:4200>

---

## Colors

Yes, I did steal some colors from the color palette of GitHub, as it's perfect, and I'm no good designer.
