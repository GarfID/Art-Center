package ru.garfid.artcenter.core.secure.model.logic.component.chain.manager

import org.springframework.security.authentication.*
import org.springframework.stereotype.Component
import ru.garfid.artcenter.core.secure.model.logic.component.chain.provider.JwtProvider
import ru.garfid.artcenter.core.secure.model.logic.component.chain.provider.LoginJwtProvider

@Component
class JwtAuthManager(
        jwtProvider: JwtProvider,
        loginJwtProvider: LoginJwtProvider
) : ProviderManager(listOf(loginJwtProvider, jwtProvider))