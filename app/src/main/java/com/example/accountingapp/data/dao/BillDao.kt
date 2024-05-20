package com.example.accountingapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.accountingapp.data.model.Bill

@Dao
interface BillDao {
    @Query("SELECT * FROM Bill")
    fun getAll(): List<Bill>

    @Insert
    fun insert(bill: Bill)

    @Update
    fun update(bill: Bill)

    @Delete
    fun delete(bill: Bill)
}