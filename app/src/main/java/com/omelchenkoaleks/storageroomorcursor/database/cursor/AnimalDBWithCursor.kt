package com.omelchenkoaleks.storageroomorcursor.database.cursor

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.omelchenkoaleks.storageroomorcursor.database.room.AnimalDao
import com.omelchenkoaleks.storageroomorcursor.model.Animal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnimalDBWithCursor(context: Context) :
    SQLiteOpenHelper(context, "db_animals", null, 1), AnimalDao {
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db?.execSQL(
                "CREATE TABLE IF NOT EXISTS db_animals (" +
                        "id	INTEGER NOT NULL," +
                        "name TEXT NOT NULL," +
                        "age INTEGER NOT NULL," +
                        "breed TEXT NOT NULL," +
                        "PRIMARY KEY(id AUTOINCREMENT)" +
                        ");"
            )
        } catch (exception: SQLException) {
            Log.e("TAG", "SQLException", exception)
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    @SuppressLint("Range")
    private suspend fun getAnimalList(order: String): List<Animal> {
        return withContext(Dispatchers.IO) {
            val animalList = mutableListOf<Animal>()
            val db = writableDatabase
            val selectQuery = "SELECT * FROM table_animal ORDER BY $order"
            val cursor = db.rawQuery(selectQuery, null)
            cursor?.let {
                if (cursor.moveToFirst()) {
                    do {
                        val id = cursor.getInt(cursor.getColumnIndex("id"))
                        val name = cursor.getString(cursor.getColumnIndex("name"))
                        val age = cursor.getInt(cursor.getColumnIndex("age"))
                        val breed = cursor.getString(cursor.getColumnIndex("breed"))
                        animalList.add(Animal(id, name, age, breed))
                    } while (cursor.moveToNext())
                }
            }
            cursor.close()
            animalList
        }
    }

    override fun getAnimalsOrderBy(order: String): LiveData<List<Animal>> {
        return liveData {
            emit(getAnimalList(order))
        }
    }

    @SuppressLint("Range")
    override fun getAnimals(id: Int): LiveData<Animal?> {
        val animalLiveData = MutableLiveData<Animal>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM table_animal WHERE id = $id"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val age = cursor.getInt(cursor.getColumnIndex("age"))
                    val breed = cursor.getString(cursor.getColumnIndex("breed"))
                    val animal = Animal(id, name, age, breed)
                    animalLiveData.value = animal
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return animalLiveData
    }

    override suspend fun addAnimal(animal: Animal) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("name", animal.name)
        values.put("age", animal.age)
        values.put("breed", animal.breed)
        db.insert("table_animal", null, values)
        db.close()
    }

    override suspend fun updateAnimal(animal: Animal) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("id", animal.id)
        values.put("name", animal.name)
        values.put("age", animal.age)
        values.put("breed", animal.breed)
        db.update("table_animal", values, "id=?", arrayOf(animal.id.toString()))
        db.close()
    }

    override suspend fun deleteAnimal(animal: Animal) {
        val db = writableDatabase
        db.delete("table_animal", "id=?", arrayOf(animal.id.toString()))
        db.close()
    }
}