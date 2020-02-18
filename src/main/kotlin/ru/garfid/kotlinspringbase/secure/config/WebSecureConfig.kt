package ru.garfid.kotlinspringbase.secure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.AuthenticationFilter

@Configuration
@EnableWebSecurity
open class WebSecureConfig : WebSecurityConfigurerAdapter() {
}