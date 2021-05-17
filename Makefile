.PHONY: eureka eureka-% provider provider-% consumer all monitor-setup monitor gateway

EUREKAS ?= eurekaa:17001,eurekab:17002,eurekac:17003

PROVIDERS ?= :18001,:18002,:18003

CONSUMERS ?= :19001

GATEWAYS ?= :20001

default: all

eureka:
	node scripts/booter.js eureka-server $(EUREKAS)

provider:
	node scripts/booter.js provider-service $(PROVIDERS) $(EUREKAS)

consumer:
	node scripts/booter.js consumer-service $(CONSUMERS) $(EUREKAS)

gateway:
	node scripts/booter.js gateway $(GATEWAYS) $(EUREKAS)

all: eureka provider consumer

monitor-setup:
	docker-compose -f ./scripts/docker-compose.monitor.yml up -d

monitor:
	docker-compose -f ./scripts/docker-compose.monitor.yml start -d

