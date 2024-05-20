package com.example.accountingapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import java.util.Date

@Entity(foreignKeys = [ForeignKey(
    entity = Category::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("categoryId"),
    onDelete = ForeignKey.CASCADE
)])
data class Bill(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val amount: Double,  // 金额
    val date: Date,  // 日期
    val categoryId: Int,  // 类别ID
    val note: String?  // 备注，这里假设备注是可选的
)
