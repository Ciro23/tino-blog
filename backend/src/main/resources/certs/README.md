## Do this to make authentication working!

Place here your keys files!  
Open a new terminal in this directory and execute the following commands.

### Generate the private key
```shell
openssl genpkey -algorithm RSA -out private.pem -pkeyopt rsa_keygen_bits:4096
```

### Generate the public key
```shell
openssl rsa -pubout -in private.pem -out public.pem
```
