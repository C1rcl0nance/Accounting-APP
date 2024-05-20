package com.example.accountingapp

import android.app.Application
import androidx.room.Room
import com.example.accountingapp.data.database.AppDatabase
import com.example.accountingapp.data.model.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountingApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize the database
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        // Insert the predefined categories
        CoroutineScope(Dispatchers.IO).launch {
            db.categoryDao().insertAll(*PREDEFINED_CATEGORIES.toTypedArray())
        }
    }

    companion object {
        val PREDEFINED_CATEGORIES = listOf(
            Category(name = "购物消费", type = "支出", iconResource = R.drawable.shopping),
            Category(name = "食品餐饮", type = "支出", iconResource = R.drawable.food),
            Category(name = "出行交通", type = "支出", iconResource = R.drawable.shopping),
            Category(name = "休闲娱乐", type = "支出", iconResource = R.drawable.relax),
            Category(name = "居家生活", type = "支出", iconResource = R.drawable.home),
            Category(name = "文化教育", type = "支出", iconResource = R.drawable.education),
            Category(name = "送礼人情", type = "支出", iconResource = R.drawable.gift),
            Category(name = "健康医疗", type = "支出", iconResource = R.drawable.health),
            Category(name = "其他", type = "支出", iconResource = R.drawable.other),
            Category(name = "中奖", type = "收入", iconResource = R.drawable.gift),
            Category(name = "理财盈利", type = "收入", iconResource = R.drawable.financial),
            Category(name = "礼金人情", type = "收入", iconResource = R.drawable.money),
            Category(name = "借入", type = "收入", iconResource = R.drawable.borrow),
            Category(name = "奖金", type = "收入", iconResource = R.drawable.bonus),
            Category(name = "兼职外快", type = "收入", iconResource = R.drawable.parttime),
            Category(name = "工资", type = "收入", iconResource = R.drawable.salary),
            Category(name = "补贴", type = "收入", iconResource = R.drawable.allowance),
            Category(name = "报销", type = "收入", iconResource = R.drawable.reimbursement),
            Category(name = "其他", type = "收入", iconResource = R.drawable.other),
            // add more predefined categories here
        )
    }
}
