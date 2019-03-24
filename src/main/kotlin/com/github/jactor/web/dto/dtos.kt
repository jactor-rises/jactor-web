package com.github.jactor.web.dto

import java.time.LocalDateTime

data class AddressDto(
        var id: Long? = null,
        var createdBy: String = "todo",
        var timeOfCreation: LocalDateTime = LocalDateTime.now(),
        var modifiedBy: String = "todo",
        var timeOfModification: LocalDateTime = LocalDateTime.now(),
        var zipCode: String? = null,
        var addressLine1: String? = null,
        var addressLine2: String? = null,
        var addressLine3: String? = null,
        var city: String? = null,
        var country: String? = null
)

data class PersonDto(
        var id: Long? = null,
        var createdBy: String = "todo",
        var timeOfCreation: LocalDateTime = LocalDateTime.now(),
        var modifiedBy: String = "todo",
        var timeOfModification: LocalDateTime = LocalDateTime.now(),
        var address: AddressDto? = null,
        var locale: String? = null,
        var firstName: String? = null,
        var surname: String? = null,
        var description: String? = null
)

data class UserDto(
        var id: Long? = null,
        var createdBy: String = "todo",
        var timeOfCreation: LocalDateTime = LocalDateTime.now(),
        var modifiedBy: String = "todo",
        var timeOfModification: LocalDateTime = LocalDateTime.now(),
        var person: PersonDto? = null,
        var emailAddress: String? = null,
        var username: String? = null,
        var userType: UserType = UserType.ACTIVE
)

enum class UserType {
    ADMIN, ACTIVE, INACTIVE
}
