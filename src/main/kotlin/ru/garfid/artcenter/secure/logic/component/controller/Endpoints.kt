package ru.garfid.artcenter.secure.logic.component.controller

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import ru.garfid.artcenter.secure.logic.service.SignUpUtilService

@RestController
@RequestMapping("security")
class Endpoints (
        val singUpUtilService: SignUpUtilService
) {

    data class SignUpRequest(
            val username: String,
            val password: String
    )

    @PostMapping("/signup")
    @ResponseBody
    fun register(@RequestBody body: SignUpRequest): String {
        if(singUpUtilService.isNewUsernameValid(body.username) && singUpUtilService.isNewPasswordValid(body.password)) {
            if(singUpUtilService.signUp(body.username, body.password)) {
                return "{\"ok\": \"true\", \"result\": \"success\"}"
            }
        }

        return "{\"ok\": \"false\", \"error\": \"error while creating user\"}"
    }

    @RequestMapping(value = ["/whois"], method = [RequestMethod.POST])
    fun checkCurrentUser(authentication: Authentication?): String {
        return authentication?.principal?.toString() ?: "{\"ok\": \"false\", \"error\": \"no auth user\"}"
    }
}