package com.example.accountingapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.accountingapp.data.dao.CategoryDao
import com.example.accountingapp.data.model.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryRepository(private val categoryDao: CategoryDao) {
    val allExpenseCategories: MutableLiveData<List<Category>> = MutableLiveData()
    val allIncomeCategories: MutableLiveData<List<Category>> = MutableLiveData()

    init {
        getAllCategories()
    }

    private fun getAllCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            allExpenseCategories.postValue(categoryDao.getAllExpenseCategories())
            allIncomeCategories.postValue(categoryDao.getAllIncomeCategories())
        }
    }

    suspend fun insert(category: Category) {
        categoryDao.insert(category)
    }
}
