package com.omelchenkoaleks.storageroomorcursor

import android.app.Application
import com.omelchenkoaleks.storageroomorcursor.database.AnimalsRepository
import com.omelchenkoaleks.storageroomorcursor.database.DaoFactory

class AnimalApp: Application() {
    override fun onCreate() {
        super.onCreate()
        AnimalsRepository.initRepository(DaoFactory(this))
    }
}