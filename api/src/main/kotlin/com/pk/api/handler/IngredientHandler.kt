package com.pk.api.handler

import com.pk.api.models.Ingredient
import com.pk.api.repository.IngredientRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@Component
class IngredientHandler(private val ingredientRepository: IngredientRepository) {

    fun getAll(request: ServerRequest): Mono<ServerResponse> {
        val ingredients: Flux<Ingredient> = ingredientRepository.findAll()

        return ok().body(ingredients)
    }

    fun create(request: ServerRequest): Mono<ServerResponse> {
        val ingredient = request.bodyToMono<Ingredient>()

        return ok().body(ingredientRepository.saveAll(ingredient).toMono())
    }
}