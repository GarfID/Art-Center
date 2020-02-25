package ru.garfid.artcenter.secure.model.entity

import lombok.ToString
import ru.garfid.artcenter.core.model.entity.util.BaseEntity
import ru.garfid.artcenter.core.model.entity.SystemUser
import ru.garfid.artcenter.secure.model.entity.util.SecurityUserRole
import javax.persistence.*

@Entity(name = "SecurityUser")
@Table(name = "security_auth")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@ToString
class SecurityUser(
        @OneToOne(fetch = FetchType.EAGER)
        var user: SystemUser = SystemUser(),
        @Column(nullable = false)
        val password: String = "guest",
        @Column(nullable = false)
        val passwordExpired: Boolean = false,
        @Column(nullable = false)
        val locked: Boolean = false,
        @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "role")
        val roles: MutableList<SecurityUserRole> = mutableListOf()
): BaseEntity() {
        override fun toString(): String {
                return "{\"user\": " + user.toString() + ", \"locked\": \"$locked\"}"
        }
}