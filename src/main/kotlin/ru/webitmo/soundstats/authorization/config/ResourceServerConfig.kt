package ru.webitmo.soundstats.authorization.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain
import ru.webitmo.soundstats.authorization.SpotifyTokenIntrospector

@Configuration
@EnableWebSecurity
class ResourceServerConfig {
    @Bean
    @Order(1)
    fun resourceFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            securityMatcher("/api/**")
            authorizeRequests {
                authorize("/api/**", authenticated)
                authorize(anyRequest, permitAll)
            }
            oauth2ResourceServer {
                opaqueToken {
                    introspector = SpotifyTokenIntrospector()
                }
            }
            csrf { disable() }
        }
        return http.build()
    }
}