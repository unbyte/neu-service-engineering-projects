.PHONY: eureka eureka-% provider provider-% consumer all

EUREKAS ?= eurekaa:17001,eurekab:17002,eurekac:17003

PROVIDERS ?= :18001,:18002,:18003

CONSUMERS ?= :19001

default: all

eureka:
	node scripts/booter.js eureka-server $(EUREKAS)

provider:
	node scripts/booter.js provider-service $(PROVIDERS) $(EUREKAS)

consumer:
	node scripts/booter.js consumer-service $(CONSUMERS) $(EUREKAS)

all: eureka provider consumer