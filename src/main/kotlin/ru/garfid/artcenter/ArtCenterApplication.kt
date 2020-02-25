package ru.garfid.artcenter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class ArtCenterApplication

fun main(args: Array<String>) {
    runApplication<ArtCenterApplication>(*args)
}
