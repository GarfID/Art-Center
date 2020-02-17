package ru.garfid.kotlinspringbase.secure.model.container

data class AuthRequest(
        val login: String,
        val password: String
)