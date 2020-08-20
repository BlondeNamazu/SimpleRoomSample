package com.blondenamazu.simpleroomsample.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Dog::class,
        Owner::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun petDao(): PetDAO
}