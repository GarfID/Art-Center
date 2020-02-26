package ru.garfid.artcenter.core.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.ToString
import ru.garfid.artcenter.core.model.entity.linkers.AuthorityAccessLevelEnum
import ru.garfid.artcenter.core.model.entity.util.BaseEntity
import javax.persistence.*

@Entity(name = "User")
@Table(name = "core_user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@ToString
class SystemUser(
        @Column(nullable = false, unique = true)
        val username: String = "guest",
        @Column(nullable = false, unique = true)
        val displayName: String = "guest",
        @Column(nullable = false)
        @JsonIgnore
        val password: String = "guest",
        @Column(nullable = false)
        val passwordExpired: Boolean = false,
        @Column(nullable = false)
        val locked: Boolean = false,
        @Enumerated(EnumType.STRING)
        val globalTranslatorCapability: AuthorityAccessLevelEnum = AuthorityAccessLevelEnum.NONE,
        @Column(nullable = false)
        val isModerator: Boolean = false
) : BaseEntity() {
    override fun toString(): String {
        return "{\"displayName\": \"$displayName\"}"
    }
}