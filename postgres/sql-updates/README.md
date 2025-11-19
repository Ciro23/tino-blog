# Database migrations

This directory contains all the `.sql` files to migrate an existing database when updating the application.  
For example, when updating from `1.0.1` to `1.1.0`, it's necessary to manually execute the statements from `v1_1_0.sql`
in the existing database.

> When jumping multiple versions, it's still necessary to execute all the migration statements for the versions in
> between.
