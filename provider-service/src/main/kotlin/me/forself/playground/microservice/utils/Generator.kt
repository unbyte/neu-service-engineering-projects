package me.forself.playground.microservice.utils

import java.util.*

fun generateUUID() = UUID.randomUUID().toString().replace("-", "")

fun generateNowTimestamp() = System.currentTimeMillis() / 1000