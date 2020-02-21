package ru.garfid.kotlinspringbase.secure.logic

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.MessageSource
import org.springframework.context.MessageSourceAware
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.security.authentication.*
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.CredentialsContainer
import org.springframework.security.core.SpringSecurityMessageSource
import org.springframework.stereotype.Component
import org.springframework.util.Assert

@Component
class AuthManager(
        val providers: List<AuthenticationProvider> = listOf<AuthenticationProvider>(JwtProvider()),
        val parent: AuthenticationManager? = null) : AuthenticationManager, MessageSourceAware, InitializingBean {

    private var eventPublisher: AuthenticationEventPublisher = NullEventPublisher()
    private var messages = SpringSecurityMessageSource.getAccessor()

    init {
        Assert.notNull(providers, "providers list cannot be null")
        checkState()
    }

    var isEraseCredentialsAfterAuthentication = true

    override fun afterPropertiesSet() {
        checkState()
    }

    private fun checkState() {
        require(!(parent == null && providers.isEmpty())) {
            ("A parent AuthenticationManager or a list "
                    + "of AuthenticationProviders is required")
        }
    }

    /**
     * Attempts to authenticate the passed [Authentication] object.
     *
     *
     * The list of [AuthenticationProvider]s will be successively tried until an
     * `AuthenticationProvider` indicates it is capable of authenticating the
     * type of `Authentication` object passed. Authentication will then be
     * attempted with that `AuthenticationProvider`.
     *
     *
     * If more than one `AuthenticationProvider` supports the passed
     * `Authentication` object, the first one able to successfully
     * authenticate the `Authentication` object determines the
     * `result`, overriding any possible `AuthenticationException`
     * thrown by earlier supporting `AuthenticationProvider`s.
     * On successful authentication, no subsequent `AuthenticationProvider`s
     * will be tried.
     * If authentication was not successful by any supporting
     * `AuthenticationProvider` the last thrown
     * `AuthenticationException` will be rethrown.
     *
     * @param authentication the authentication request object.
     *
     * @return a fully authenticated object including credentials.
     *
     * @throws AuthenticationException if authentication fails.
     */

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val toTest: Class<out Authentication> = authentication.javaClass
        var lastException: AuthenticationException? = null
        var parentException: AuthenticationException? = null
        var result: Authentication? = null
        var parentResult: Authentication? = null
        val debug = logger.isDebugEnabled

        for (provider in providers) {
            if (!provider.supports(toTest)) {
                continue
            }
            if (debug) {
                logger.debug("Authentication attempt using "
                        + provider.javaClass.name)
            }
            try {
                result = provider.authenticate(authentication)
                if (result != null) {
                    copyDetails(authentication, result)
                    break
                }
            } catch (e: AccountStatusException) {
                prepareException(e, authentication)
                throw e
            } catch (e: InternalAuthenticationServiceException) {
                prepareException(e, authentication)
                throw e
            } catch (e: AuthenticationException) {
                lastException = e
            }
        }
        if (result == null && parent != null) { // Allow the parent to try.
            try {
                parentResult = parent.authenticate(authentication)
                result = parentResult
            } catch (e: ProviderNotFoundException) {
                // ignore as we will throw below if no other exception occurred prior to
                // calling parent and the parent
                // may throw ProviderNotFound even though a provider in the child already
                // handled the request
            } catch (e: AuthenticationException) {
                parentException = e
                lastException = parentException
            }
        }
        if (result != null) {
            if (isEraseCredentialsAfterAuthentication
                    && result is CredentialsContainer) {
                // Authentication is complete. Remove credentials and other secret data
                // from authentication
                (result as CredentialsContainer).eraseCredentials()
            }
            // If the parent AuthenticationManager was attempted and successful than it will publish an AuthenticationSuccessEvent
            // This check prevents a duplicate AuthenticationSuccessEvent if the parent AuthenticationManager already published it
            if (parentResult == null) {
                eventPublisher.publishAuthenticationSuccess(result)
            }
            return result
        }
        // Parent was null, or didn't authenticate (or throw an exception).
        if (lastException == null) {
            lastException = ProviderNotFoundException(messages.getMessage(
                    "ProviderManager.providerNotFound", arrayOf<Any>(toTest.name),
                    "No AuthenticationProvider found for {0}"))
        }
        // If the parent AuthenticationManager was attempted and failed than it will publish an AbstractAuthenticationFailureEvent
        // This check prevents a duplicate AbstractAuthenticationFailureEvent if the parent AuthenticationManager already published it
        if (parentException == null) {
            prepareException(lastException, authentication)
        }
        throw lastException
    }

    private fun prepareException(ex: AuthenticationException, auth: Authentication) {
        eventPublisher.publishAuthenticationFailure(ex, auth)
    }

    /**
     * Copies the authentication details from a source Authentication object to a
     * destination one, provided the latter does not already have one set.
     *
     * @param source source authentication
     * @param dest the destination authentication object
     */
    private fun copyDetails(source: Authentication, dest: Authentication) {
        if (dest is AbstractAuthenticationToken && dest.getDetails() == null) {
            dest.details = source.details
        }
    }

    override fun setMessageSource(messageSource: MessageSource) {
        messages = MessageSourceAccessor(messageSource)
    }

    fun setAuthenticationEventPublisher(
            eventPublisher: AuthenticationEventPublisher) {
        Assert.notNull(eventPublisher, "AuthenticationEventPublisher cannot be null")
        this.eventPublisher = eventPublisher
    }

    private class NullEventPublisher : AuthenticationEventPublisher {
        override fun publishAuthenticationFailure(exception: AuthenticationException,
                                                  authentication: Authentication) {
        }

        override fun publishAuthenticationSuccess(authentication: Authentication) {}
    }

    companion object {
        private val logger = LogFactory.getLog(ProviderManager::class.java)
    }
}
