package com.example.android.hydration

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WaterRecord(
    @PrimaryKey
    @NonNull
    val day: String,
    @NonNull
    var glasses: Int
    )