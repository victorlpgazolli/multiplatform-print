package dev.victorlpgazolli.printer

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform