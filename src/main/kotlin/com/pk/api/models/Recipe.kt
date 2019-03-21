package com.pk.api.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Recipe(

        @Id val id: String? = null,
        val name: String,
        val author: String,
        val description: String,
        val recipeIngredientSet: Set<RecipeIngredient>

)