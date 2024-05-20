package com.example.accountingapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.accountingapp.data.database.AppDatabase
import com.example.accountingapp.data.model.Category
import com.example.accountingapp.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CategoryRepository
    val allExpenseCategories: LiveData<List<Category>>
    private val allIncomeCategories: LiveData<List<Category>>

    init {
        val categoriesDao = AppDatabase.getDatabase(application).categoryDao()
        repository = CategoryRepository(categoriesDao)
        allExpenseCategories = repository.allExpenseCategories
        allIncomeCategories = repository.allIncomeCategories
    }

    fun insert(category: Category) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(category)
    }
}