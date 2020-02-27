package ru.garfid.artcenter.core.secure.model.logic.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.security.Key
import java.util.*
import java.util.Calendar.HOUR_OF_DAY


object JwtService {
    private val secret = "temporaryExampleSecretWitchIsVerySecureAndComprehansible. Sure fucking thing!".toByteArray()
    private val key: Key = Keys.hmacShaKeyFor(secret)

    fun makeTokenString(username: String): String {
        val now = Date()

        return Jwts.builder().setIssuedAt(now).setExpiration(addHoursToJavaUtilDate(now, 5)).setSubject(username).signWith(key).compact()
    }

    fun getClaims(token: String): Jws<Claims> {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
    }

    fun getSubject(token: String): String {
        return getClaims(token).body.subject
    }

    fun checkToken(token: String): Boolean {
        return try {
            getClaims(token)
            true
        } catch (e: JwtException) {
            false
        }
    }

    fun refresh(oldToken: String): String {
        return makeTokenString(getSubject(oldToken))
    }

    private fun addHoursToJavaUtilDate(date: Date, hours: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(HOUR_OF_DAY, hours)
        return calendar.time
    }
}