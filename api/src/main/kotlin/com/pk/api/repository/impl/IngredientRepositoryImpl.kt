package com.pk.api.repository.impl

import com.pk.api.repository.spec.IngredientRepository
import org.springframework.context.annotation.Profile

@Profile("!mock")
interface IngredientRepositoryImpl : IngredientRepository