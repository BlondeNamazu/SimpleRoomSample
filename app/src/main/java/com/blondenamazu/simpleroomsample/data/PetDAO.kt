package com.blondenamazu.simpleroomsample.data

import androidx.room.*

@Dao
interface PetDAO {
    @Query(value = "SELECT * FROM Dog")
    fun getAllDogs(): List<Dog>

    @Query(value = "SELECT * FROM Owner")
    fun getAllOwners(): List<Owner>

    @Transaction
    @Query(value = "SELECT * FROM Owner")
    fun getOneToOneOwnerAndDog(): List<OneToOneOwnerAndDog>

    @Transaction
    @Query(value = "SELECT * FROM Owner")
    fun getOneToManyOwnerAndDogs(): List<OneToManyOwnerAndDogs>

    @Insert
    fun insertDog(dog: Dog)

    @Insert
    fun insertOwner(owner: Owner)

    @Transaction
    fun deleteAllData() {
        deleteAllDogs()
        deleteAllOwners()
    }

    @Query(value = "DELETE FROM Dog")
    fun deleteAllDogs()

    @Query(value = "DELETE FROM Owner")
    fun deleteAllOwners()
}