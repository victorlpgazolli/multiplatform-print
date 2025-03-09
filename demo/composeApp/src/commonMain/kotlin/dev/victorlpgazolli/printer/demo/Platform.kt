package dev.victorlpgazolli.printer.demo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform