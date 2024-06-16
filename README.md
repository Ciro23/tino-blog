## Compiling from source
This application requires connecting to a MySQL database, which must be created manually.
The default name is _tino-blog_, changing it also requires to update the name reference in `application.properties`.  
The first admin user must be created manually inserting a new row in the `users` table, but before doing that, the password must be encrypted using _Bcrypt_ with strength "12": this [online generator](https://bcrypt-generator.com/) comes very handy.
```mysql
INSERT INTO users (id, email, password, username) 
VALUES (x'6516feadbed44947b69e70509e0dc763', 'admin@test.org', '$2a$12$FlMmt3gTI1gWlaAtwj/CiesZ/jD14ROHiG3YdS.5.ZC25GQ0V5ONq', 'admin');
# the 'x" before the id string is required as it's stored as binary(16)
```
