package rs.android.task4.repository.cursor

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import rs.android.task4.COLUMN_AGE
import rs.android.task4.COLUMN_BREED
import rs.android.task4.COLUMN_NAME
import rs.android.task4.repository.IAnimalDao
import rs.android.task4.repository.Animal

class AnimalCursorDao(context: Context) : IAnimalDao {
    private val cursorDb = AnimalSQLiteDatabase(context)

    override fun getAllSortByName(): Flow<List<Animal>> = flow {
        emit(
            cursorDb.getAllSortBy(
                COLUMN_NAME
            )
        )
    }

    override fun getAllSortByAge(): Flow<List<Animal>> = flow {
        emit(
            cursorDb.getAllSortBy(
                COLUMN_AGE
            )
        )
    }

    override fun getAllSortByBreed(): Flow<List<Animal>> = flow {
        emit(
            cursorDb.getAllSortBy(
                COLUMN_BREED
            )
        )
    }

    override suspend fun add(animal: Animal) = cursorDb.insert(animal)

    override suspend fun delete(animal: Animal) = cursorDb.delete(animal)

    override fun insertData(data: List<Animal>) {
        Log.i(this.javaClass.simpleName, "")
    }

    override suspend fun update(animal: Animal) = cursorDb.update(animal)
}