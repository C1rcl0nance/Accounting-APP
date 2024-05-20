package com.example.accountingapp

import android.app.Application
import com.example.accountingapp.data.database.AppDatabase

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize the database
        AppDatabase.getDatabase(this)
    }
}
