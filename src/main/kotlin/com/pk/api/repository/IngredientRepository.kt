package com.pk.api.repository

import com.pk.api.models.Ingredient
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface IngredientRepository : ReactiveMongoRepository<Ingredient, String>