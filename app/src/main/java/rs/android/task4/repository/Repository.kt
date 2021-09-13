package rs.android.task4.repository

import kotlinx.coroutines.flow.Flow
import rs.android.task4.COLUMN_AGE
import rs.android.task4.COLUMN_BREED
import rs.android.task4.COLUMN_NAME
import rs.android.task4.DATABASE_SOURCE_NAME_ROOM
import rs.android.task4.repository.cursor.AnimalCursorDao
import rs.android.task4.repository.room.AnimalDatabase

class Repository(
    private val db: AnimalDatabase,
    private val cursorDao: AnimalCursorDao
) {
    private var dao: IAnimalDao = db.animalDao

    fun getAllSortBy(columnName: String): Flow<List<Animal>> = when (columnName) {
        COLUMN_NAME -> dao.getAllSortByName()
        COLUMN_AGE -> dao.getAllSortByAge()
        COLUMN_BREED -> dao.getAllSortByBreed()
        else -> dao.getAllSortByName()
    }

    suspend fun save(animal: Animal) = dao.add(animal)

    suspend fun update(animal: Animal) = dao.update(animal)

    suspend fun delete(animal: Animal) = dao.delete(animal)

    fun changeSource(nameSource: String) {
        dao = if (nameSource == DATABASE_SOURCE_NAME_ROOM) {
            cursorDao
        } else {
            db.animalDao
        }
    }
}