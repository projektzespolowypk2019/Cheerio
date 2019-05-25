package com.pk.api.handler

import com.pk.api.repository.UnitRepository
import com.pk.api.models.Unit
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.net.URI

@Component
class UnitHandler(private val unitRepository: UnitRepository) {

    fun getAll(request: ServerRequest): Mono<ServerResponse> {
        val units: Flux<Unit> = unitRepository.findAll()

        return ServerResponse.ok().body(units)
    }

    fun create(request: ServerRequest): Mono<ServerResponse> {
        val unit = request.bodyToMono<Unit>()

        return ServerResponse.created(URI.create(request.path()))
                .body(unitRepository.saveAll(unit).toMono())
    }
}