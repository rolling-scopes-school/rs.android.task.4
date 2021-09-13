package rs.android.task4.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import rs.android.task4.DATABASE_NAME
import rs.android.task4.ListAnimals
import rs.android.task4.repository.Animal
import java.util.concurrent.Executors

@Database(entities = [Animal::class], version = 1)
abstract class AnimalDatabase : RoomDatabase() {
    abstract val animalDao: AnimalRoomDao

    companion object {
        @Volatile
        private var INSTANCE: AnimalDatabase? = null

        fun getInstance(context: Context): AnimalDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: create(context).also { INSTANCE = it }
            }

        fun create(context: Context) = Room.databaseBuilder(
            context,
            AnimalDatabase::class.java,
            DATABASE_NAME
        ).addCallback(object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Executors.newSingleThreadScheduledExecutor()
                    .execute {
                        getInstance(context).animalDao.insertData(ListAnimals.animals)
                    }
            }
        }).build()

    }


}