package com.example.android.hydration

import android.app.Application

class HydrationApplication: Application() {

    private val database by lazy {WaterDatabase.getDatabase(this)}
    val repository by lazy {WaterRepository(database.waterDao())}
}