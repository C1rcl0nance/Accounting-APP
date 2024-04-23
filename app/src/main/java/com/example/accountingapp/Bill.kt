package com.example.accountingapp

import android.content.Context
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.time.LocalDateTime

/*
收入与支出为一级类
收入有二级类，支出有二级类和三级类
具体类见“类.km”
*/

//收入与支出
enum class BillType {
    INCOME, //收入
    EXPENSE //支出
}

//具体大类
data class MainCategory(
    val name: String,   //类名
    val billType: BillType  //收入与支出
)

//具体子类
data class SubCategory(
    val name: String,   //类名
    val parent: String    //父类名
)

//账单类
data class Bill(
    val amount: Double,
    val type: BillType,
    val timestamp: LocalDateTime,
    val note: String?,
    val mainCategory: MainCategory,
    val subCategory: SubCategory
)

//账单管理器
class BillManager(private val context: Context) {
    private val bills = mutableListOf<Bill>()   //帐单列表
    private val mainCategories = mutableListOf<MainCategory>()  //大类列表
    private val subCategories = mutableListOf<SubCategory>()  //子类列表

    init {
        loadPredefinedCategories()    //预定义类别
    }

    //预定义类别
    private fun loadPredefinedCategories() {
        //将categories的字符串存在xmlString中
        val xmlString = context.resources.openRawResource(R.raw.categories).bufferedReader().use { it.readText() }
        //创建XML解析器
        val factory = XmlPullParserFactory.newInstance()
        //创建XmlPullParser解析器并设置为解析xmlString
        val parser = factory.newPullParser()
        parser.setInput(StringReader(xmlString))
        //获取当前解析事件的类型
        var eventType = parser.eventType
        //存储解析的类别名称和类型
        lateinit var name: String
        lateinit var billType: BillType
        lateinit var parent: String
        //开始循环直到XML文件结尾
        while(eventType != XmlPullParser.END_DOCUMENT) {
            when(eventType) {
                XmlPullParser.START_TAG -> {
                    //如果起始标签为category，则将类别名称和类型存到相应变量中
                    if (parser.name == "mainCategory") {
                        name = parser.getAttributeValue(null, "name")
                        val billTypeString = parser.getAttributeValue(null, "billType")
                        billType = BillType.valueOf(billTypeString)
                    }
                    if (parser.name == "subCategory") {
                        name = parser.getAttributeValue(null, "name")
                        parent = parser.getAttributeValue(null, "parent")
                    }
                }
                XmlPullParser.END_TAG -> {
                    //如果结束标签为category，则将两个变量封装为category对象并加入categories列表中
                    if (parser.name == "mainCategory") {
                        mainCategories.add(MainCategory(name, billType))
                    }
                    if (parser.name == "category") {
                        subCategories.add(SubCategory(name, parent))
                    }
                }
            }
            eventType = parser.next()
        }
    }

    //增加账单
    fun addBill(bill: Bill) {
        bills.add(bill)
    }

    //增加大类
    fun addMainCategory(mainCategory: MainCategory) {
        mainCategories.add(mainCategory)
    }

    //增加子类
    fun addSubCategory(subCategory: SubCategory) {
        subCategories.add(subCategory)
    }
}