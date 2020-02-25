package ru.garfid.artcenter.core.model.entity

import lombok.ToString
import ru.garfid.artcenter.core.model.entity.util.BaseEntity
import ru.garfid.artcenter.secure.model.entity.SecurityUser
import javax.persistence.*

@Entity(name = "User")
@Table(name = "core_user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@ToString
class SystemUser(
        val displayName: String = "guest",
        @OneToOne(
                cascade = [CascadeType.ALL],
                orphanRemoval = true,
                fetch = FetchType.LAZY,
                optional = false
        )
        var auth: SecurityUser? = null
) : BaseEntity() {
        override fun toString(): String {
                return "{\"displayName\": \"$displayName\"}"
        }
}