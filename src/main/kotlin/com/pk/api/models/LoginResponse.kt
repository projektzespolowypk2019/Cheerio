package com.pk.api.models

data class LoginResponse(

        val code: String,
        val nickname: String,
        val token_type: String,
        val access_token: String

)