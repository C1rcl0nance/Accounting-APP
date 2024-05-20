package com.example.accountingapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import com.example.accountingapp.data.model.Category

@Dao
interface CategoryDao {
    @Insert
    fun insert(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg categories: Category)

    @Update
    fun update(category: Category)

    @Delete
    fun delete(category: Category)

    @Query("SELECT * FROM Category WHERE type = '支出'")
    fun getAllExpenseCategories(): List<Category>

    @Query("SELECT * FROM Category WHERE type = '收入'")
    fun getAllIncomeCategories(): List<Category>

}