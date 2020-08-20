package com.blondenamazu.simpleroomsample.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface PetDAO {
    @Query(value = "SELECT * FROM Dog")
    fun getAllDogs(): List<Dog>

    @Query(value = "SELECT * FROM Owner")
    fun getAllOwners(): List<Owner>

    @Transaction
    @Query(value = "SELECT * FROM Owner")
    fun getOneToOneOwnerAndDog(): List<OneToOneOwnerAndDog>

    @Insert
    fun insertDog(dog: Dog)

    @Insert
    fun insertOwner(owner: Owner)
}