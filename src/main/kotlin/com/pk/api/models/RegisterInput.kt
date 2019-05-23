package com.pk.api.models

data class RegisterInput(

        val email: String,
        var password: String,
        val nickname: String

)