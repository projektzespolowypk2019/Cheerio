package com.pk.api.repository

import com.pk.api.models.Unit
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UnitRepository : ReactiveMongoRepository<Unit, String>