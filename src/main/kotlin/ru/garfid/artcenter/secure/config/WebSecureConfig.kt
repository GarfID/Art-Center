package ru.garfid.artcenter.secure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter
import ru.garfid.artcenter.secure.logic.component.chain.filter.AuthJwtFilter
import ru.garfid.artcenter.secure.logic.component.chain.filter.LoginJwtAuthFilter
import ru.garfid.artcenter.secure.logic.component.chain.manager.JwtAuthManager


@Configuration
@EnableWebSecurity
class WebSecureConfig(
        private val authManager: JwtAuthManager,
        private val loginJwtAuthFilter: LoginJwtAuthFilter,
        private val jwtAuthFilter: AuthJwtFilter
) : WebSecurityConfigurerAdapter() {

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return authManager
    }

    override fun configure(http: HttpSecurity) {
        http.
                csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.POST, "/security/signup", "/security/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().disable()
                .addFilterBefore(jwtAuthFilter, SecurityContextHolderAwareRequestFilter::class.java)
                .addFilterBefore(loginJwtAuthFilter, AuthJwtFilter::class.java)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }
}