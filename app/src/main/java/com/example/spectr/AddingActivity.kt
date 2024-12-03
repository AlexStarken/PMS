package com.example.spectr

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class AddingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_adding)

        val returnBack: Button = findViewById(R.id.back_button)
        val createNote: Button = findViewById(R.id.create_button)
        val materialName: EditText = findViewById(R.id.material_name)
        val materialText: EditText = findViewById(R.id.material_text)
        val materialDesc: EditText = findViewById(R.id.material_desc)

        returnBack.setOnClickListener {
            val intent = Intent(this, ItemsActivity::class.java)
            startActivity(intent)
        }

        createNote.setOnClickListener {
            val name = materialName.text.toString()
            val text = materialText.text.toString()
            val desc = materialDesc.text.toString()

            // Создание Intent для передачи данных обратно
            val resultIntent = Intent()
            resultIntent.putExtra("MATERIAL_NAME", name)
            resultIntent.putExtra("MATERIAL_TEXT", text)
            resultIntent.putExtra("MATERIAL_DESC", desc)
            setResult(RESULT_OK, resultIntent)
            finish() // Закрытие активности и возврат
        }
    }
}