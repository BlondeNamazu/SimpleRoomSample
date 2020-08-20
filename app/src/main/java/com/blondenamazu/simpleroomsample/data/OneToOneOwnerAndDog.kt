package com.blondenamazu.simpleroomsample.data

import androidx.room.Embedded
import androidx.room.Relation

data class OneToOneOwnerAndDog(
    @Embedded val owner: Owner,
    @Relation(
        parentColumn = "ownerId",
        entityColumn = "dogOwnerId"
    )
    val dog: Dog
)