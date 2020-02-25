package ru.garfid.artcenter.core.model.entity.util

import javax.persistence.*

@MappedSuperclass
open class SimpleEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null
)