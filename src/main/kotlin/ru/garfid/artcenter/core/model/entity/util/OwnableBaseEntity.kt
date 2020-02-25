package ru.garfid.artcenter.core.model.entity.util

import ru.garfid.artcenter.core.model.entity.SystemUser
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class OwnableBaseEntity(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "ownder_id", nullable = false)
        val owner: SystemUser = SystemUser()
): BaseEntity()