package ru.garfid.kotlinspringbase.secure.model.entity

import org.springframework.security.core.GrantedAuthority
import ru.garfid.kotlinspringbase.core.model.entity.util.BaseEntity
import ru.garfid.kotlinspringbase.secure.model.entity.util.AuthorityAccessLevelEnum
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = "security_capability")
class Capability(
        @Enumerated(EnumType.STRING)
        val level: AuthorityAccessLevelEnum
): GrantedAuthority, BaseEntity() {
    override fun getAuthority(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}