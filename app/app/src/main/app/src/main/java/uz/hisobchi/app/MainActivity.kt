package uz.hisobchi.app

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var balance = 0.0
    private var totalIncome = 0.0
    private var totalExpense = 0.0

    private lateinit var balanceText: TextView
    private lateinit var incomeText: TextView
    private lateinit var expenseText: TextView
    private lateinit var historyText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(30, 30, 30, 30)

        val title = TextView(this)
        title.text = "💰 ҲИСОБЧИ"
        title.textSize = 28f

        balanceText = TextView(this)
        balanceText.textSize = 24f
        balanceText.setPadding(0, 30, 0, 20)

        incomeText = TextView(this)
        expenseText = TextView(this)

        val incomeButton = Button(this)
        incomeButton.text = "➕ Кирим қўшиш"

        val expenseButton = Button(this)
        expenseButton.text = "➖ Чиқим қўшиш"

        historyText = TextView(this)
        historyText.textSize = 16f
        historyText.setPadding(0, 25, 0, 0)

        layout.addView(title)
        layout.addView(balanceText)
        layout.addView(incomeText)
        layout.addView(expenseText)
        layout.addView(incomeButton)
        layout.addView(expenseButton)
        layout.addView(historyText)

        setContentView(layout)

        updateScreen()

        incomeButton.setOnClickListener {
            showAmountDialog(true)
        }

        expenseButton.setOnClickListener {
            showAmountDialog(false)
        }
    }

    private fun showAmountDialog(isIncome: Boolean) {

        val input = EditText(this)
        input.hint = "Суммани киритинг"
        input.inputType = 2

        val title =
            if (isIncome) "Кирим қўшиш"
            else "Чиқим қўшиш"

        AlertDialog.Builder(this)
            .setTitle(title)
            .setView(input)
            .setPositiveButton("Сақлаш") { _, _ ->

                val amount =
                    input.text.toString().toDoubleOrNull()

                if (amount != null && amount > 0) {

                    if (isIncome) {
                        totalIncome += amount
                        balance += amount
                    } else {
                        totalExpense += amount
                        balance -= amount
                    }

                    updateScreen()
                }
            }
            .setNegativeButton("Бекор қилиш", null)
            .show()
    }

    private fun updateScreen() {

        balanceText.text =
            "Баланс: ${formatMoney(balance)} сўм"

        incomeText.text =
            "Кирим: ${formatMoney(totalIncome)} сўм"

        expenseText.text =
            "Чиқим: ${formatMoney(totalExpense)} сўм"

        historyText.text =
            """
            📋 Операциялар

            Ҳозирча операциялар тарихи кейинги версияда қўшилади.

            📦 Савдо
            👥 Қарзлар
            📊 Ҳисобот
            """.trimIndent()
    }

    private fun formatMoney(value: Double): String {
        return String.format("%,.0f", value)
            .replace(",", " ")
    }
}
