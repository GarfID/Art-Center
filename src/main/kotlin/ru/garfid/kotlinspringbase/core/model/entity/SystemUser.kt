package ru.garfid.kotlinspringbase.core.model.entity

import ru.garfid.kotlinspringbase.core.model.entity.util.BaseEntity
import ru.garfid.kotlinspringbase.secure.model.entity.SecurityUser
import javax.persistence.*

@Entity(name = "User")
@Table(name = "core_user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
class SystemUser(
        val displayName: String = "guest",
        @OneToOne(
                cascade = [CascadeType.ALL],
                orphanRemoval = true,
                fetch = FetchType.LAZY,
                optional = false
        )
        val auth: SecurityUser? = null
) : BaseEntity()