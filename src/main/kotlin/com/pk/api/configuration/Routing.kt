package com.pk.api.configuration

import com.pk.api.handler.IngredientHandler
import com.pk.api.handler.RecipeHandler
import com.pk.api.handler.UnitHandler
import com.pk.api.handler.UserHandler
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

    @Bean
    fun recipeRouter(handler: RecipeHandler) =
            router {
                ("/recipes" and accept(MediaType.APPLICATION_JSON_UTF8)).nest {
                    GET("/", handler::getAll)
                    POST("/", handler::create)
                }
            }

    @Bean
    fun unitRouter(handler: UnitHandler) =
            router {
                ("/units" and accept(MediaType.APPLICATION_JSON_UTF8)).nest {
                    GET("/", handler::getAll)
                    POST("/", handler::create)
                }
            }

    @Bean
    fun userRouter(handler: UserHandler) =
            router {
                ("/users" and accept(MediaType.APPLICATION_JSON_UTF8)).nest {
                    GET("/", handler::login)
                    POST("/", handler::create)
                }
            }
}