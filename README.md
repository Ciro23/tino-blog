## Compiling from source

### Requirements

- Java 21
- PostgreSQL 16
- Angular CLI

### Steps

1. The database must be created manually using PostgreSQL: the default name is "tino-blog", but it can be customized
   using environment variables (see point number 3): the tables will be created by Spring during the first run.
2. The first admin user is created automatically (the default password is "password"), but it's very important to change
   its password (along the other details) via the `users` table. The password must be encrypted using _Bcrypt_ with
   strength "12": this [online generator](https://bcrypt-generator.com/) comes very handy.
   ```postgresql
   UPDATE users
   set username = 'your_username',
       email = 'your@email.org',
       password = '$2a$12$Hp1V.oI.VKiOBSkb85sXMepcUmwZeyzWxZbpgg1JwMTfklaGeeoB2'
   where id = '4c7dbc23-b524-4dd2-95f0-c0cb974588c7';
   ```
3. Configure the following environment variables for the backend (it can be done using IntelliJ IDEA "run configuration"
   or via system, for example running `export MY_ENV_VAR=123` on Linux and macOS:
    - DB_HOST (default "localhost")
    - DB_PORT (default "5432")
    - DB_NAME (default "tino-blog")
    - DB_USER
    - DB_PASSWORD
4. Navigate to the repository root and compile the backend:
   ```shell
   mvn clean package
   ```
5. Run the backend:
   ```shell
   java -jar target/tino-blog-0.0.1-SNAPSHOT.war
   ```
6. Navigate to the `frontend` directory and run it:
   ```shell
   npm install
   ng serve
   ```

#### Customize Bootstrap theme

Some Bootstrap colors were changed in the file `frontend/src/custom_bootstrap/custom.scss`.  
If the SASS is changed, then it needs to be compiled again in CSS:

1. Install SASS:
   ```shell
   npm install -g sass
   ```
2. Compile the `.scss` file to `.css`:
   ```shell
   sass --watch frontend/src/custom_bootstrap/custom.scss frontend/src/custom_bootstrap/custom.css
   ```

The full reference can be found [here](https://getbootstrap.com/docs/5.3/customize/sass/).
