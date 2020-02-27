package ru.garfid.artcenter.core.secure.model.logic.service.permissions

import ru.garfid.artcenter.room.model.entity.AuthorityAccessLevelEnum
import ru.garfid.artcenter.room.model.entity.AuthorityBlockEnum

interface BlockPermissionResolver {
    val targetBlock: AuthorityBlockEnum

    fun isCapableOf(id: Long, permission: AuthorityAccessLevelEnum): Boolean
}