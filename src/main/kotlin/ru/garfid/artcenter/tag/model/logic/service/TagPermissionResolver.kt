package ru.garfid.artcenter.tag.model.logic.service

import org.springframework.stereotype.Service
import ru.garfid.artcenter.room.model.entity.AuthorityAccessLevelEnum
import ru.garfid.artcenter.core.secure.model.logic.service.permissions.BlockPermissionResolver
import ru.garfid.artcenter.room.model.entity.AuthorityBlockEnum

@Service
class TagPermissionResolver: BlockPermissionResolver {
    override val targetBlock: AuthorityBlockEnum = AuthorityBlockEnum.TAGS

    override fun isCapableOf(id: Long, permission: AuthorityAccessLevelEnum): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}