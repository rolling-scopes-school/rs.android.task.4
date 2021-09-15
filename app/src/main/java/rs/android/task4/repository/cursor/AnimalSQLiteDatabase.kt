package rs.android.task4.repository.cursor

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import rs.android.task4.*
import rs.android.task4.repository.Animal

private const val TABLE_NAME = "animals_cursor"
private const val COLUMN_ID = "id"
private const val CREATE_TABLE_SQL =
    "CREATE TABLE IF NOT EXISTS $TABLE_NAME (id INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME VARCHAR(30), $COLUMN_AGE INTEGER, $COLUMN_BREED VARCHAR(30))"
private const val INSERT_ANIMAL_SQL =
    "INSERT INTO $TABLE_NAME ($COLUMN_NAME, $COLUMN_AGE, $COLUMN_BREED) VALUES "
private const val APPENDING_DB_CURSOR = "(DB cursor)"


class AnimalSQLiteDatabase(context: Context) : SQLiteOpenHelper
    (context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_SQL)
        ListAnimals.animals.forEach { db.execSQL(INSERT_ANIMAL_SQL + "('${it.name} $APPENDING_DB_CURSOR', ${it.age}, '${it.breed}')") }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    private fun getCursorSortBy(columnName: String): Cursor {
        return readableDatabase.query(TABLE_NAME, null, null, null, null, null, columnName)
    }

    fun getAllSortBy(columnName: String): List<Animal> {
        val animals = mutableListOf<Animal>()
        getCursorSortBy(columnName).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val animalId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                    val animalName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                    val animalAge = cursor.getInt(cursor.getColumnIndex(COLUMN_AGE))
                    val animalBreed = cursor.getString(cursor.getColumnIndex(COLUMN_BREED))
                    animals.add(Animal(animalId, animalName, animalAge, animalBreed))
                } while (cursor.moveToNext())
            }
        }
        return animals
    }

    fun insert(animal: Animal) {
        writableDatabase.use { it.insert(TABLE_NAME, null, getContentValues(animal)) }
    }

    fun update(animal: Animal) {
        val updateCv = getContentValues(animal)
        updateCv.put(COLUMN_ID, animal.id)
        writableDatabase.use {
            it.update(
                TABLE_NAME,
                updateCv,
                "id=${animal.id}",
                null
            )
        }
    }

    fun delete(animal: Animal) {
        writableDatabase.use { it.delete(TABLE_NAME, "id=${animal.id}", null) }
    }

    private fun getContentValues(animal: Animal): ContentValues {
        val cv = ContentValues()
        cv.put(COLUMN_NAME, animal.name)
        cv.put(COLUMN_AGE, animal.age)
        cv.put(COLUMN_BREED, animal.breed)
        return cv
    }

}