package ru.garfid.kotlinspringbase.secure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import ru.garfid.kotlinspringbase.secure.logic.AuthManager
import ru.garfid.kotlinspringbase.secure.logic.AuthFilter
import ru.garfid.kotlinspringbase.secure.logic.JwtProvider

@Configuration
@EnableWebSecurity
open class WebSecureConfig(
        private val jwtProvider: JwtProvider,
        private val authManager: AuthManager
) : WebSecurityConfigurerAdapter() {

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return AuthManager()
    }

    override fun configure(http: HttpSecurity) {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and().csrf().disable()
                .authenticationProvider(jwtProvider)
                .addFilterBefore(AuthFilter(authManager), UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(jwtProvider)
    }
}