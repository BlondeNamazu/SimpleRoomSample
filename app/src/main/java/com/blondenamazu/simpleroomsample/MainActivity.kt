package com.blondenamazu.simpleroomsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.room.Room
import com.blondenamazu.simpleroomsample.data.AppDatabase
import com.blondenamazu.simpleroomsample.data.Dog
import com.blondenamazu.simpleroomsample.data.Owner
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var database: AppDatabase
    var listItems: List<String> = listOf()
    lateinit var adapter: ArrayAdapter<String>
    var latestOwnerId: Long = 0
    var latestDogId: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = Room
            .databaseBuilder(this, AppDatabase::class.java, "petDB")
            .allowMainThreadQueries()
            .build()
        updateList()
        latestOwnerId = database
            .petDao()
            .getAllOwners()
            .map { it.ownerId + 1 }
            .max() ?: 0
        latestDogId = database
            .petDao()
            .getAllDogs()
            .map { it.dogId + 1 }
            .max() ?: 0
        insert_owner_button.setOnClickListener {
            val name = edit_owner_name.text.toString()
            edit_owner_name.text.clear()
            val newOwner = Owner(
                ownerId = latestOwnerId,
                name = name
            )
            //println("attempt to insert id: ${latestOwnerId}, name: ${name}")
            latestOwnerId += 1
            database
                .petDao()
                .insertOwner(newOwner)
        }
        insert_dog_button.setOnClickListener {
            val ownerId = edit_dogOwner_id.text.toString().toLong()
            edit_dogOwner_id.text.clear()
            val name = edit_dog_name.text.toString()
            edit_dog_name.text.clear()
            val newDog = Dog(
                dogId = latestDogId,
                dogOwnerId = ownerId,
                name = name
            )
            //println("attempt to insert id: ${latestDogId}, name: ${name}")
            latestDogId += 1
            database
                .petDao()
                .insertDog(newDog)
        }
        show_dog_button.setOnClickListener {
            listItems = database
                .petDao()
                .getAllDogs()
                .map { dog -> "id: ${dog.dogId} Owner id: ${dog.dogOwnerId} name: ${dog.name}" }
            //listItems.forEach { println(it) }
            updateList()
        }
        show_owner_button.setOnClickListener {
            listItems = database
                .petDao()
                .getAllOwners()
                .map { owner -> "id: ${owner.ownerId} name: ${owner.name}" }
            //listItems.forEach { println(it) }
            updateList()
        }
        show_one_to_one.setOnClickListener {
            listItems = database
                .petDao()
                .getOneToOneOwnerAndDog()
                .map { "${it.owner.name} has ${it.dog.name}" }
            updateList()
        }
        delete_all_button.setOnClickListener {
            database
                .petDao()
                .deleteAllData()
            listItems = emptyList()
            latestDogId = 0
            latestOwnerId = 0
            updateList()
        }
    }
    private fun updateList(){
        show_result.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listItems
        )
    }
}

