package com.pk.api.handler

import com.pk.api.models.LoginInput
import com.pk.api.models.LoginResponse
import com.pk.api.models.RegisterInput
import com.pk.api.models.User
import com.pk.api.repository.UserRepository
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
                                        HttpHeaders.AUTHORIZATION,
                                        "Basic Zm9pb19hdXRoX3NlcnZpY2U6NzBmTmRPTG1sS1pNZnBEYTlLQ1YvaUdCL2ZmN3hrMldPNXc4WFNlTURkYz0="
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
                    .uri("/users")
                    .header(
                            HttpHeaders.CONTENT_TYPE,
                            MediaType.APPLICATION_JSON_VALUE
                    )
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjMxMzIxNTg3NjUsInVzZXJfbmFtZSI6ImZvaW9fYXBpIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BVVRIX0NSRUFURV9VU0VSIl0sImp0aSI6ImJkNTdhNmRlLTcyZGYtNDAyZS1iN2NkLTVmNjU3ZjI5YTFkZiIsImNsaWVudF9pZCI6ImZvaW9fYXV0aF9zZXJ2aWNlIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.xt1vgrB8clS5rTav6x0oBmTtqaGg51NOcns_WYaoIU1aJO5pnb6byCfcz4T7XsgRy4bZTWXf_Jmu3PHFBmbI_wwS8zfeaR2vD1ijn6qX5aKY9dJ2yscll1xhFKlSJpHSgEReUMDU9hft9gIjEz5ife4NUmH5KGsH0u0xgmUaoNOtMNJZZG4usTt4QLwBXawocLd0X8-WP6bb-xTHNbr5DkqkTS3cSClK4xjbEW-E0nlSFY8-RAfEBKbAvfynR40sB4VDE_8j-uSQyGxxAWHgMkBGGwNHwXtPqwEyWpamO7q2LIDZH4RuFVWEUo6QNtD3KzU26Mp8sSft2Gk5jUeR4A"
                    )
                    .body(BodyInserters.fromPublisher(
                            request.body(BodyExtractors.toMono(RegisterInput::class.java)),
                            RegisterInput::class.java
                    ))
                    .exchange()
                    .flatMap { response ->
                        val user: Mono<User> = response.bodyToMono(User::class.java)
                                .doOnNext { authServiceUser ->
                                    authServiceUser.id = null
                                    userRepository.save(authServiceUser).doOnNext { createdUser ->
                                        authServiceUser.id = createdUser.id
                                    }
                                }
                        ServerResponse.created(URI.create("/users")).body(user)
                    }
                    .onErrorResume { ServerResponse.badRequest().build() }
}