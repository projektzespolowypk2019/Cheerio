package com.pk.api.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(

        @Id var id: String? = null,
        val code: String = "",
        val email: String = "",
        val nickname: String = ""

)