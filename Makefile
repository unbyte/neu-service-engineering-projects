.PHONY: eureka provider consumer all monitor-setup monitor gateway config

EUREKAS ?= eurekaa:17001,eurekab:17002,eurekac:17003

PROVIDERS ?= :18001,:18002,:18003

CONSUMERS ?= :19001

GATEWAYS ?= :20001

CONFIG ?= :21001

default: all

eureka:
	node scripts/booter.js eureka-server $(EUREKAS) $(EUREKAS) $(CONFIG)

provider:
	node scripts/booter.js provider-service $(PROVIDERS) $(EUREKAS) $(CONFIG)

consumer:
	node scripts/booter.js consumer-service $(CONSUMERS) $(EUREKAS) $(CONFIG)

gateway:
	node scripts/booter.js gateway $(GATEWAYS) $(EUREKAS) $(CONFIG)

config:
	node scripts/booter.js config-server $(CONFIG) $(EUREKAS) $(CONFIG)

all: eureka provider consumer gateway config

monitor-setup:
	docker-compose -f ./scripts/docker-compose.monitor.yml up -d

monitor:
	docker-compose -f ./scripts/docker-compose.monitor.yml start -d

