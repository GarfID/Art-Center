package ru.garfid.artcenter.core.model.entity.util

import ru.garfid.artcenter.core.model.entity.SystemUser
import java.util.*
import javax.persistence.*

@MappedSuperclass
open class BaseEntity(
        @Column(nullable = false)
        @Temporal(TemporalType.TIMESTAMP)
        val createdAt: Date = Date(),
        @Temporal(TemporalType.TIMESTAMP)
        val deletedAt: Date? = null,
        @ManyToOne(fetch = FetchType.LAZY, optional = true)
        val deletedBy: SystemUser? = null
): SimpleEntity()