package ru.garfid.kotlinspringbase.secure.model.entity

import ru.garfid.kotlinspringbase.core.model.entity.util.BaseEntity
import ru.garfid.kotlinspringbase.core.model.entity.SystemUser
import ru.garfid.kotlinspringbase.secure.model.entity.util.SecurityUserRole
import javax.persistence.*

@Entity(name = "SecurityUser")
@Table(name = "security_auth")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
class SecurityUser(
        @OneToOne(fetch = FetchType.EAGER)
        @MapsId
        var user: SystemUser? = null,
        @Column(nullable = false)
        var username: String = "guest",
        @Column(nullable = false)
        val password: String = "guest",
        @Column(nullable = false)
        val passwordExpired: Boolean = false,
        @Column(nullable = false)
        val locked: Boolean = false,
        @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "role")
        val roles: MutableList<SecurityUserRole> = mutableListOf()
): BaseEntity()