package com.pk.api.repository

import com.pk.api.models.Recipe
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface RecipeRepository : ReactiveMongoRepository<Recipe, String>