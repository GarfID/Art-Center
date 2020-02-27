package ru.garfid.artcenter.core.secure.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import ru.garfid.artcenter.core.model.container.ResponseContainer
import ru.garfid.artcenter.core.secure.model.logic.service.SignUpUtilService


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
    fun register(@RequestBody body: SignUpRequest): ResponseEntity<ResponseContainer> {
        if(body.username.length < 4) {
            val response = ResponseContainer(false, "bad username", null)
            return ResponseEntity(response, HttpStatus.BAD_REQUEST)
        }

        if(singUpUtilService.isUsernameTaken(body.username)) {
            val response = ResponseContainer(false, "username already taken", null)
            return ResponseEntity(response, HttpStatus.CONFLICT)
        }

        if(!singUpUtilService.isNewPasswordValid(body.password)) {
            val response = ResponseContainer(false, "bad password", null)
            return ResponseEntity(response, HttpStatus.BAD_REQUEST)
        }

        if(singUpUtilService.signUp(body.username, body.password)) {
            return ok(ResponseContainer(true, null, "successfully registered"))
        }

        return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @RequestMapping(value = ["/login"], method = [RequestMethod.POST])
    fun loginConfirmation(authentication: Authentication?): ResponseEntity<ResponseContainer> {
        if(authentication == null || authentication.principal == null) {
            val response = ResponseContainer(false, "no such username and password", null)
            return ResponseEntity(response, HttpStatus.BAD_REQUEST)
        }

        return ok(ResponseContainer(true, null, authentication.credentials as String))
    }

    @GetMapping("/me")
    fun currentUser(authentication: Authentication): ResponseEntity<ResponseContainer> {
        return ok(ResponseContainer(true,null, authentication))
    }
}