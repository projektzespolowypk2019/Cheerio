package com.pk.api.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(

        @Id val id: String? = null,
        val code: String,
        val nickname: String,
        val token_type: String,
        val access_token: String

)