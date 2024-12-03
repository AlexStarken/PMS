package com.example.spectr

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Fragment : AppCompatActivity() {
    external fun getProcessedData(itemTitle: String?, itemText: String?): String

    init {
        System.loadLibrary("mylibrary")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        val title: TextView = findViewById(R.id.item_list_title_one)
        val text: TextView = findViewById(R.id.item_list_text)
        val button: Button = findViewById(R.id.button_back)

        val itemTitle = intent.getStringExtra("itemTitle")
        val itemText = intent.getStringExtra("itemText")

        // Получаем обработанные данные из C
        val processedData = getProcessedData(itemTitle, itemText)

        // Устанавливаем текст на TextView
        title.text = processedData

        button.setOnClickListener {
            val intent = Intent(this, ItemsActivity::class.java)
            startActivity(intent)
        }
    }
}