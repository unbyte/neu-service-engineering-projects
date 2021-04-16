# For Homework 1

## Run
0. make sure that Java, Gradle, Node and Make have been installed.

1. run `scripts/setup-hosts.ps1` to set hosts on Windows or add hosts manually on *nix
   
2. export env variables including at least `DB_HOST` `DB_PORT` `DB_NAME` `DB_USER` `DB_PASS`
    
    - can use `env.local` file containing `key=value` line by line instead 
   
3. run `make -j3`

## Scale Instances

edit `EUREKAS` `PROVIDERS` `CONSUMERS` in `Makefile`

format: `[host]:port[,...]`

## Full Env Variables

- `DB_HOST` `DB_PORT` `DB_NAME` `DB_USER` `DB_PASS`
  
- `EUREKA_HOST` `EUREKA_PORT`
  
- `PROVIDER_PORT`

- `CONSUMER_PORT`

## Example env.local

```text
DB_HOST=localhost
DB_PORT=3306
DB_NAME=dev
DB_USER=dev
DB_PASS=password
```