package ru.garfid.artcenter.core.secure.model.logic.service.permissions

import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import ru.garfid.artcenter.core.secure.model.data.container.JwToken
import ru.garfid.artcenter.room.model.entity.AuthorityAccessLevelEnum
import ru.garfid.artcenter.room.model.entity.AuthorityBlockEnum
import ru.garfid.artcenter.room.model.entity.AuthorityGlobalEnum
import java.io.Serializable
import java.util.*

@Service
class JwtPermissionEvaluator(
        blockPermissionResolvers: List<BlockPermissionResolver>
) : PermissionEvaluator {
    val permissionResolverMap: EnumMap<AuthorityBlockEnum, BlockPermissionResolver> = EnumMap<AuthorityBlockEnum, BlockPermissionResolver>(AuthorityBlockEnum::class.java)

    init {
        blockPermissionResolvers.forEach { permissionResolverMap[it.targetBlock] = it }
    }

    override fun hasPermission(authentication: Authentication, targetDomainObject: Any, permission: Any): Boolean {
        if (authentication is JwToken) {
            if (authentication.getDetailsAsBaseUserDetails().systemUser.isModerator) {
                return true
            }

            var permissionObject: AuthorityAccessLevelEnum = AuthorityAccessLevelEnum.NONE
            AuthorityAccessLevelEnum.values().firstOrNull { it.name == permission}.let { if(it != null) permissionObject = it }

            if (targetDomainObject is AuthorityGlobalEnum && permissionObject != AuthorityAccessLevelEnum.NONE) {
                when (targetDomainObject) {
                    AuthorityGlobalEnum.GLOBAL_TRANSLATE -> {
                        return authentication.getDetailsAsBaseUserDetails().systemUser.globalTranslatorCapability == permissionObject
                    }
                    else -> {
                        return false
                    }
                }
            }
        }

        return false
    }

    override fun hasPermission(authentication: Authentication, targetId: Serializable, targetType: String, permission: Any): Boolean {
        var permissionObject: AuthorityAccessLevelEnum = AuthorityAccessLevelEnum.NONE
        AuthorityAccessLevelEnum.values().firstOrNull { it.name == permission}.let { if(it != null) permissionObject = it }

        if (authentication is JwToken && permissionObject != AuthorityAccessLevelEnum.NONE && targetId is Long) {
            if (authentication.getDetailsAsBaseUserDetails().systemUser.isModerator) {
                return true
            }

            var isValidType = false

            AuthorityBlockEnum.values()
                    .firstOrNull { it.toString() == targetType }
                    .let { if (it != null) isValidType = true }

            if (isValidType) {
                return permissionResolverMap[AuthorityBlockEnum.valueOf(targetType)]?.isCapableOf(targetId, permissionObject) ?: false

            }
        }

        throw IllegalArgumentException("!(authentication is JwToken && permission is AuthorityAccessLevelEnum && targetId is Long)")
    }
}