package ru.garfid.artcenter.room.controller.endpoint

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.garfid.artcenter.core.model.container.ResponseContainer

@RestController
@RequestMapping("room")
class RoomEndpoint {

    @GetMapping("details/{id}")
    //@PreAuthorize("false")
    @PreAuthorize("hasPermission(#id, 'POST', 'VIEW')")
    fun authorityTest(@PathVariable id: Long): ResponseEntity<ResponseContainer> {
        return ok(ResponseContainer(true, null, "Access granted"))
    }
}