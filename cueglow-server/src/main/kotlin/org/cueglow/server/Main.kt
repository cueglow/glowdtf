package org.cueglow.server

import io.javalin.Javalin


fun main(args: Array<String>) {
    println("Starting Server")
    val app = Javalin.create().start(7000)
    app.get("/") { ctx -> ctx.result("Hello World") }
}
