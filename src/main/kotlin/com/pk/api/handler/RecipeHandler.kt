package com.pk.api.handler

import com.pk.api.models.Recipe
import com.pk.api.repository.RecipeRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.created
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.net.URI

@Component
class RecipeHandler(private val recipeRepository: RecipeRepository) {

    fun getAll(request: ServerRequest): Mono<ServerResponse> {
        val recipes: Flux<Recipe> = recipeRepository.findAll()

        return ok().body(recipes)
    }

    fun create(request: ServerRequest): Mono<ServerResponse> {
        val recipe = request.bodyToMono<Recipe>()

        return created(URI.create(request.path()))
                .body(recipeRepository.saveAll(recipe).toMono())
    }
}