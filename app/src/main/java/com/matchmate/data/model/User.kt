package com.matchmate.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val email: String,
    val gender: String?,
    @Embedded(prefix = "name_")
    val name: Name?,
    @Embedded(prefix = "location_")
    val location: Location?,
    @Embedded(prefix = "dob_")
    val dob: Dob?,
    @Embedded(prefix = "picture_")
    val picture: Picture?,

    // Offline support
    var isAccepted: Boolean,
    var isDeclined: Boolean,
)