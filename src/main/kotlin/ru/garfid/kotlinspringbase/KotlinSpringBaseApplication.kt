package ru.garfid.kotlinspringbase

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class KotlinSpringBaseApplication

fun main(args: Array<String>) {
    runApplication<KotlinSpringBaseApplication>(*args)
}
