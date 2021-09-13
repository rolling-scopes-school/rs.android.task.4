package rs.android.task4.repository.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import rs.android.task4.repository.Animal
import rs.android.task4.repository.IAnimalDao

@Dao
interface AnimalRoomDao: IAnimalDao {

    @Query("SELECT * FROM animals_room ORDER BY name")
    override fun getAllSortByName(): Flow<List<Animal>>

    @Query("SELECT * FROM animals_room ORDER BY age")
    override fun getAllSortByAge(): Flow<List<Animal>>

    @Query("SELECT * FROM animals_room ORDER BY breed")
    override fun getAllSortByBreed(): Flow<List<Animal>>

    @Insert
    override suspend fun add(animal: Animal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun update(animal: Animal)

    @Delete
    override suspend fun delete(animal: Animal)

    @Insert
    override fun insertData(data: List<Animal>)

}