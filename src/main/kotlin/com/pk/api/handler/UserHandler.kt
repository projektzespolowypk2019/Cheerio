package com.pk.api.handler

import com.pk.api.models.LoginInput
import com.pk.api.models.LoginResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

@Component
class UserHandler {

    fun login(request: ServerRequest): Mono<ServerResponse> =
            request.body(BodyExtractors.toMono(LoginInput::class.java))
                    .flatMap { input ->
                        authClient
                                .post()
                                .uri {
                                    it.path("/oauth/token")
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
                                        "Authorization",
                                        "Basic Zm9pb19hdXRoX3NlcnZpY2U6NzBmTmRPTG1sS1pNZnBEYTlLQ1YvaUdCL2ZmN3hrMldPNXc4WFNlTURkYz0="
                                )
                                .exchange()
                                .flatMap {
                                    val user = it.bodyToMono(LoginResponse::class.java)
                                    ServerResponse.ok().body(user)
                                }
                    }

    fun create(request: ServerRequest): Mono<ServerResponse> {
        TODO()
    }
}