package ru.garfid.artcenter.secure.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import ru.garfid.artcenter.secure.logic.component.chain.filter.AuthJwtFilter
import ru.garfid.artcenter.secure.logic.component.chain.filter.LoginJwtAuthFilter
import ru.garfid.artcenter.secure.logic.component.chain.provider.JwtProvider


@Configuration
@EnableWebSecurity
open class WebSecureConfig(
        private val loginJwtAuthFilter: LoginJwtAuthFilter,
        private val jwtAuthFilter: AuthJwtFilter
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                .addFilterBefore(jwtAuthFilter, AnonymousAuthenticationFilter::class.java)
                .addFilterBefore(loginJwtAuthFilter, AuthJwtFilter::class.java)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/security/signup", "/security/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().logout().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
    }
}