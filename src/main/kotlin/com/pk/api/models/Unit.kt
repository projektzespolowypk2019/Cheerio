package com.pk.api.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Unit(

        @Id val id: String? = null,
        val name: String

)