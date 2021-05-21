package me.forself.playground.microservice.model

class Response(
    val code: Int = 0,
    val data: Any? = null,
    val msg: String? = null,
)

fun withResponse(fn: () -> Any?) = try {
    Response(data = fn())
} catch (e: Throwable) {
    Response(msg = e.message)
}
