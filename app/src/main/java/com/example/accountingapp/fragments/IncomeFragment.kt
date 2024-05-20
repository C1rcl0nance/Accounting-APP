package com.example.accountingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.room.Room
import com.example.accountingapp.CategoryAdapter
import com.example.accountingapp.R
import com.example.accountingapp.data.database.AppDatabase
import com.example.accountingapp.data.model.Category
import com.example.accountingapp.viewmodel.CategoryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IncomeFragment : Fragment() {
    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_income, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the database
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database-name"
        ).build()

        // Get the predefined categories
        var categoryList = listOf<Category>()
        CoroutineScope(Dispatchers.IO).launch {
            categoryList = db.categoryDao().getAllIncomeCategories()
        }

        // Find the GridView in the layout
        val gridView: GridView = view.findViewById(R.id.income_GV)

        // Create an adapter
        val adapter = CategoryAdapter(requireContext(), categoryList)

        // Set the adapter on the GridView
        gridView.adapter = adapter
    }
}
R