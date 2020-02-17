package ru.garfid.kotlinspringbase.core.model.entity.util

import ru.garfid.kotlinspringbase.core.model.entity.SystemUser
import java.util.*
import javax.persistence.*

@MappedSuperclass
open class BaseEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @Column(nullable = false)
        @Temporal(TemporalType.TIMESTAMP)
        val createdAt: Date = Date(),
        @Temporal(TemporalType.TIMESTAMP)
        val deletedAt: Date? = null,
        @ManyToOne(fetch = FetchType.LAZY, optional = true)
        val deletedBy: SystemUser? = null
)