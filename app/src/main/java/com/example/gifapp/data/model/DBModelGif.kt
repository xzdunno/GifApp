package com.example.gifapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DBGif")
data class DBModelGif(
    @PrimaryKey
    val id: String,
    val url: String,
    val username: String,
    val title: String,
    val rating: String,
    val width: String,
    val height: String
)
