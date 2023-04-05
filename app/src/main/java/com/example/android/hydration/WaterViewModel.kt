package com.example.android.hydration

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class WaterViewModel(private val repository: WaterRepository): ViewModel() {

    val allRecords = repository.getAllRecords().asLiveData()

    fun insertNewRecord(record: WaterRecord) {
        viewModelScope.launch {
            repository.insert(record)
        }
    }
    fun updateRecord(record: WaterRecord) {
        viewModelScope.launch {
            repository.update(record)
        }
    }
    fun deleteRecord(record: WaterRecord) {
        viewModelScope.launch {
            repository.delete(record)
        }
    }
    fun getRecordForDay(day: String): LiveData<WaterRecord> {
        return repository.getRecordForDay(day).asLiveData()
    }
}

class WaterViewModelFactory(private val repository: WaterRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WaterViewModel::class.java)) {
            return WaterViewModel(repository) as T
        }
        throw IllegalArgumentException("$modelClass is not a WaterViewModel")
    }
}