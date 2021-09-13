package rs.android.task4

import android.app.Application
import android.content.Context
import rs.android.task4.locator.ServiceLocator
import rs.android.task4.locator.locate
import rs.android.task4.repository.Repository
import rs.android.task4.repository.cursor.AnimalCursorDao
import rs.android.task4.repository.room.AnimalDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        ServiceLocator.register<Context>(this)
        ServiceLocator.register(AnimalCursorDao(locate()))
        ServiceLocator.register(AnimalDatabase.create(locate()))
        ServiceLocator.register(Repository(locate(), locate()))
    }
}

