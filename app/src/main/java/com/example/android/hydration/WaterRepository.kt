package com.example.android.hydration

import kotlinx.coroutines.flow.Flow

class WaterRepository(private val waterDao: WaterDao) {

    suspend fun insert(record: WaterRecord) {
        waterDao.insert(record)
    }

    suspend fun update(record: WaterRecord) {
        waterDao.update(record)
    }

    suspend fun delete(record: WaterRecord) {
        waterDao.delete(record)
    }

    fun getRecordForDay(day: String): Flow<WaterRecord> {
        return waterDao.getRecordForDay(day)
    }

    fun getAllRecords(): Flow<List<WaterRecord>> {
        return waterDao.getAllRecords()
    }
}