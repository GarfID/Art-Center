package ru.garfid.kotlinspringbase.secure.model.entity

import org.springframework.security.core.GrantedAuthority
import ru.garfid.kotlinspringbase.core.model.entity.util.BaseEntity
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = "security_capability")
class Capability(
        @Enumerated(EnumType.STRING)
        val block: AuthorityBlockEnum,
        @Enumerated(EnumType.STRING)
        val level: AuthorityAccessLevelEnum
): GrantedAuthority, BaseEntity() {
    override fun getAuthority(): String {
        return "$block:$level"
    }
}

enum class AuthorityAccessLevelEnum {
    NONE, VIEW, EDIT, APPEND
}

enum class AuthorityBlockEnum {
    NONE, TAGS, IMAGE, POST, JOURNAL, COMMENT, TRANSLATE
}