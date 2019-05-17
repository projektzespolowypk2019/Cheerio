package com.pk.api.handler

import org.springframework.web.reactive.function.client.WebClient

val authClient: WebClient =
        WebClient
                .builder()
                .baseUrl("https://foio-auth-service.herokuapp.com")
                .build()