.PHONY: eureka eureka-%

eureka-%:
	gradle -q :eureka-server:bootRun --args=--spring.profiles.active=$* --parallel

eureka: eureka-a eureka-b eureka-c
