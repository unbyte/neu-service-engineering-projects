## Run
0. make sure that Java, Gradle, Node and Make have been installed.

1. run `scripts/setup-hosts.ps1` to set hosts on Windows or add hosts manually on *nix
   
2. export env variables including at least `DB_HOST` `DB_PORT` `DB_NAME` `DB_USER` `DB_PASS`

    - can use `env.local` file containing `key=value` line by line instead 

3. run `make -j3`

4. [OPTIONAL] run `make monitor` (if it's the first time, run `make monitor-setup` first) 
   to start up prometheus and grafana. It requires docker and docker-compose. The dashboard
   config for grafana is at `./scripts/monitors/grafana.dashboard.json`.

## Scale Instances

edit `EUREKAS` `PROVIDERS` `CONSUMERS` in `Makefile` or pass above kv pair as arguments to `make`

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