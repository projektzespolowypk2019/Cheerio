package com.pk.api.repository.spec

import com.pk.api.models.Ingredient
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface IngredientRepository : ReactiveMongoRepository<Ingredient, String>