package com.azabost.quest.users.repository

import com.azabost.quest.users.data.model.UserJdo

data class User(val id: Int, val firstName: String, val lastName: String)


fun User.toUserJdo(): UserJdo {
    return UserJdo(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName
    )
}