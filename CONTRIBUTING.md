# Contributing

Here's a list of special actions to take when updating the source code to contribute
to this repository.

## Changing the database

If the database's schema changes, the DDL instructions must be exported into
the file `backend/src/main/resources/schema.sql`, which is used when building
the Docker image for the database.  
This can be done with:

```shell
pg_dump -U db_username tino_blog >> schema.sql
```

Then, remove all the instructions like:

```sql
ALTER TABLE public.articles
    OWNER TO postgres;
```

otherwise it won't be possible to specify custom database users in the `.env` file.

> By removing the explicit tables' ownership to a specific user, ownership
will default to the user that creates them.  
For example, if you set your database user to `my_user`, either via the `.env`
file (for production environments) or by doing `export DB_USER=my_user`
(for local environments), then only `my_user` will be able to access and modify the
database tables.

### Migration scripts

Scripts to migrate the database must be created inside `backend/src/main/resources/sql-updates`
to keep compatibility with existing builds. Migration scripts must be written
manually inside `.sql` files.  
**Migration scripts will be executed manually by the user when updating from an
older version.**

## Customizing Bootstrap theme

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
