package com.pk.api.configuration

import com.pk.api.handler.IngredientHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class Routing {

    @Bean
    fun ingredientRouter(handler: IngredientHandler) =
            router {
                ("/ingredients" and accept(MediaType.APPLICATION_JSON_UTF8)).nest {
                    GET("/", handler::getAll)
                    POST("/", handler::create)
                }
            }
}