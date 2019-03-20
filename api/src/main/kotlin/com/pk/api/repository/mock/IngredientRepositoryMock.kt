package com.pk.api.repository.mock

import com.pk.api.models.Ingredient
import com.pk.api.repository.spec.IngredientRepository
import org.springframework.context.annotation.Profile
import org.springframework.data.domain.Example
import reactor.core.publisher.Flux
import java.util.*

@Profile("mock")
@Suppress("UNCHECKED_CAST")
interface IngredientRepositoryMock : IngredientRepository {

    override fun <S : Ingredient?> findAll(p0: Example<S>): Flux<S> {
        return Flux.just(
                Ingredient(UUID.randomUUID().toString(), "Apple") as S,
                Ingredient(UUID.randomUUID().toString(), "Banana") as S,
                Ingredient(UUID.randomUUID().toString(), "Milk") as S
        )
    }
}