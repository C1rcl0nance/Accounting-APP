package com.example.accountingapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val iconResource: Int,
    val type: String
)