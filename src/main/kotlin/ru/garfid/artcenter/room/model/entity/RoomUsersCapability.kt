package ru.garfid.artcenter.room.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import ru.garfid.artcenter.core.model.entity.util.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "core_room_user_capability")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
class RoomUsersCapability(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "room_user_id", nullable = false)
        @JsonIgnore
        val roomUser: RoomUser = RoomUser(),
        @Enumerated(EnumType.STRING)
        val block: AuthorityBlockEnum = AuthorityBlockEnum.NONE,
        @Enumerated(EnumType.STRING)
        val level: AuthorityAccessLevelEnum = AuthorityAccessLevelEnum.NONE
) : GrantedAuthority, BaseEntity() {
    override fun getAuthority(): String {
        return "${roomUser.room.id}:$block:$level"
    }
}

enum class AuthorityAccessLevelEnum {
    NONE, VIEW, EDIT, APPEND
}

enum class AuthorityGlobalEnum {
    NONE, GLOBAL_TRANSLATE
}

enum class AuthorityBlockEnum {
    NONE, TAGS, IMAGE, POST, JOURNAL, COMMENT, TRANSLATE
}