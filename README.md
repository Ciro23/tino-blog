## Compiling from source
### Requirements
- Java 21
- PostgreSQL 16
- Angular CLI

### Steps
1. The database must be created manually using PostgreSQL: the default name is "tino-blog", but it can be customized using environment variables (see point number 3): the tables will be created by Spring during the first run.
2. The first admin user must be created manually inserting a new row in the `users` table, but before doing that, the password must be encrypted using _Bcrypt_ with strength "12": this [online generator](https://bcrypt-generator.com/) comes very handy.
   ```postgresql
   INSERT INTO users (id, email, password, username) 
   VALUES ('6516feadbed44947b69e70509e0dc763', 'admin@test.org', '$2a$12$FlMmt3gTI1gWlaAtwj/CiesZ/jD14ROHiG3YdS.5.ZC25GQ0V5ONq', 'admin');
   ```
3. Configure the following environment variables for the backend (it can be done using IntelliJ IDEA "run configuration" or via system, for example running `export MY_ENV_VAR=123` on Linux and macOS:
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
