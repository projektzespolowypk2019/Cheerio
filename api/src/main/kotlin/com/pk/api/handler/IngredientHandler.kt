package com.pk.api.handler

import com.pk.api.models.Ingredient
import com.pk.api.repository.spec.IngredientRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class IngredientHandler(private val ingredientRepository: IngredientRepository) {

    fun getAll(request: ServerRequest): Mono<ServerResponse> {
        val ingredients: Flux<Ingredient> = ingredientRepository.findAll()

        return ok().body(ingredients)
    }
}