.PHONY: eureka eureka-% provider provider-% consumer all

default: all

eureka-%:
	gradle :eureka-server:bootRun --args='--spring.profiles.active=$(*)' --parallel -q

provider-%:
	gradle :provider-service:bootRun --args='--spring.profiles.active=$(*)' --parallel -q

eureka: eureka-a eureka-b eureka-c

provider: provider-a provider-b provider-c

consumer:
	gradle :consumer-service:bootRun --parallel -q

all: eureka provider consumer