package com.example.accountingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.accountingapp.R

class AddBillFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var textViewAmount: TextView
    private lateinit var buttonCancel: Button
    private lateinit var editTextNote: EditText
    private var lastOperator: String? = null
    private var num1: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_bill, container, false)

        textViewAmount = view.findViewById(R.id.textView_amount)
        buttonCancel = view.findViewById(R.id.button_cancel)

        navController = findNavController()

        /*
        切换收入支出
         */
        val buttonIncome = view.findViewById<Button>(R.id.button_income)
        val buttonExpense = view.findViewById<Button>(R.id.button_expense)

        navController.navigate(R.id.expenseFragment)
        buttonIncome.isSelected = false
        buttonExpense.isSelected = true

        buttonIncome.setOnClickListener {
            navController.navigate(R.id.incomeFragment)
            buttonIncome.isSelected = true
            buttonExpense.isSelected = false
        }

        buttonExpense.setOnClickListener {
            navController.navigate(R.id.expenseFragment)
            buttonIncome.isSelected = false
            buttonExpense.isSelected = true
        }

        /*
        添加备注
         */
        editTextNote = view.findViewById(R.id.editText_note)
        editTextNote.setOnClickListener {
            // 请求焦点
            editTextNote.requestFocus()
        }

        /*
        自定义键盘逻辑
         */
        val buttons = listOf(
            view.findViewById(R.id.button_1),
            view.findViewById(R.id.button_2),
            view.findViewById(R.id.button_3),
            view.findViewById(R.id.button_4),
            view.findViewById(R.id.button_5),
            view.findViewById(R.id.button_6),
            view.findViewById(R.id.button_7),
            view.findViewById(R.id.button_8),
            view.findViewById(R.id.button_9),
            view.findViewById(R.id.button_0),
            view.findViewById(R.id.button_dot),
            view.findViewById(R.id.button_delete),
            view.findViewById(R.id.button_plus),
            view.findViewById(R.id.button_minus),
            view.findViewById(R.id.button_times),
            view.findViewById(R.id.button_divide),
            view.findViewById(R.id.button_clear),
            buttonCancel
        )

        buttons.forEach { button ->
            button.setOnClickListener { onButtonClick(it) }
        }

        return view
    }

    private fun calculateExpression(expression: String): Double {
        val parts = expression.split(" ")
        val num2 = parts.last().toDouble()
        return when (lastOperator) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> num1 / num2
            else -> throw IllegalArgumentException("Invalid operator: $lastOperator")
        }
    }

    private fun onButtonClick(view: View) {
        if (view is Button) {
            val buttonText = view.text.toString()
            val currentText = textViewAmount.text.toString()
            val newText = when (buttonText) {
                in listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".") -> {
                    if (currentText == "0.00" || currentText == "=") {
                        buttonText
                    } else {
                        currentText + buttonText
                    }
                }
                in listOf("+", "-", "*", "/") -> {
                    if (lastOperator != null) {
                        currentText.substring(0, currentText.length - 1) + buttonText
                    } else {
                        "$currentText$buttonText "
                    }
                }
                getString(R.string.清零) -> {
                    "0.00"
                }
                getString(R.string.取消) -> {
                    when {
                        currentText.matches(Regex("^\\d*\\.?\\d+$")) -> {
                            buttonCancel.text = getString(R.string.完成)
                            currentText
                        }
                        currentText.contains(Regex("[+\\-*/]")) -> {
                            buttonCancel.text = "="
                            val result = calculateExpression(currentText)
                            "$result"
                        }
                        else -> {
                            return navController.navigate(R.id.mainFragment)
                        }
                    }
                }
                getString(R.string.删除) -> {
                    if (currentText.isNotEmpty()) {
                        if (currentText.last() == '=') {
                            currentText.dropLast(1).dropLastWhile { it.isDigit() || it == '.' || it.isWhitespace() }
                        } else {
                            currentText.dropLast(1)
                        }
                    } else {
                        currentText
                    }
                }
                else -> currentText
            }

            // 更新文本
            textViewAmount.text = newText

            // 更新最近的运算符
            if (buttonText in listOf("+", "-", "*", "/")) {
                lastOperator = buttonText
            }

            // 更新取消按钮文本
            when {
                newText.matches(Regex("^\\d*\\.?\\d+$")) -> {
                    buttonCancel.text = getString(R.string.完成)
                }
                newText.contains(Regex("[+\\-*/]")) -> {
                    buttonCancel.text = "="
                }
                newText == "0.00" -> {
                    buttonCancel.text = getString(R.string.取消)
                }
            }
        }
    }
}
