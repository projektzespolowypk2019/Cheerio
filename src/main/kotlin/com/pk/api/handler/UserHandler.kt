package com.pk.api.handler

import com.pk.api.models.LoginInput
import com.pk.api.models.LoginResponse
import com.pk.api.models.RegisterInput
import com.pk.api.models.User
import com.pk.api.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import java.net.URI

@Component
class UserHandler(private val userRepository: UserRepository) {

    @Value("\${servers.auth-service.login.uri}")
    val loginUri: String = ""

    @Value("\${servers.auth-service.login.authorization}")
    val loginAuthorization: String = ""

    @Value("\${servers.auth-service.register.uri}")
    val registerUri: String = ""

    @Value("\${servers.auth-service.register.authorization}")
    val registerAuthorization: String = ""

    fun login(request: ServerRequest): Mono<ServerResponse> =
            request.body(BodyExtractors.toMono(LoginInput::class.java))
                    .flatMap { input ->
                        authClient
                                .post()
                                .uri {
                                    it.path(loginUri)
                                            .queryParam("username", input.nickname)
                                            .queryParam("password", input.password)
                                            .queryParam("grant_type", "password")
                                            .build()
                                }
                                .header(
                                        HttpHeaders.CONTENT_TYPE,
                                        MediaType.APPLICATION_JSON_VALUE
                                )
                                .header(
                                        HttpHeaders.AUTHORIZATION,
                                        loginAuthorization
                                )
                                .exchange()
                                .flatMap {
                                    val user = it.bodyToMono(LoginResponse::class.java)
                                    ServerResponse.ok().body(user)
                                }
                    }

    fun create(request: ServerRequest): Mono<ServerResponse> =
            authClient
                    .post()
                    .uri(registerUri)
                    .header(
                            HttpHeaders.CONTENT_TYPE,
                            MediaType.APPLICATION_JSON_VALUE
                    )
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            registerAuthorization
                    )
                    .body(BodyInserters.fromPublisher(
                            request.body(BodyExtractors.toMono(RegisterInput::class.java)),
                            RegisterInput::class.java
                    ))
                    .exchange()
                    .flatMap { response ->
                        val user: Mono<User> = response.bodyToMono(User::class.java)

                        ServerResponse.created(URI.create(registerUri))
                                .body(userRepository.saveAll(user))
                    }
                    .onErrorResume { ServerResponse.badRequest().build() }
}