package com.example.spectr

import OnSwipeTouchListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ItemsActivity : AppCompatActivity() {
    private lateinit var items: MutableList<Item>
    private lateinit var itemsAdapter: ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_items)

        val itemsList: RecyclerView = findViewById(R.id.itemsList)
        val button: Button = findViewById(R.id.exit_button)
        val addButton: Button = findViewById(R.id.add_button)

        items = loadItemsFromPreferences().toMutableList()

        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        addButton.setOnClickListener {
            val intent = Intent(this, AddingActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST_CODE)
        }

        // Настройка RecyclerView
        itemsList.layoutManager = LinearLayoutManager(this)
        itemsAdapter = ItemsAdapter(items, this)
        itemsList.adapter = itemsAdapter
    }

    // Обработка результата добавления нового элемента
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            val name = data?.getStringExtra("MATERIAL_NAME") ?: ""
            val text = data?.getStringExtra("MATERIAL_TEXT") ?: ""
            val desc = data?.getStringExtra("MATERIAL_DESC") ?: ""

            // Создание нового элемента и добавление его в список
            val newItem = Item(items.size + 1, "disco", name, desc, text, 80)
            items.add(newItem)

            // Уведомление адаптера
            itemsAdapter.notifyItemInserted(items.size - 1)

            // Сохранение обновленного списка в SharedPreferences
            saveItemsToPreferences()
        }
    }

    // Функция для сохранения элементов в SharedPreferences
    fun saveItemsToPreferences() {
        val sharedPreferences = getSharedPreferences("items_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val stringBuilder = StringBuilder()

        // Сохраняем каждый элемент как строку
        for (item in items) {
            stringBuilder.append("${item.id},${item.image},${item.title},${item.desc},${item.text},${item.price};")
        }

        editor.putString("items_list", stringBuilder.toString())
        editor.apply()
    }

    // Функция для загрузки элементов из SharedPreferences
    private fun loadItemsFromPreferences(): List<Item> {
        val sharedPreferences = getSharedPreferences("items_prefs", MODE_PRIVATE)
        val data = sharedPreferences.getString("items_list", null)
        val loadedItems = mutableListOf<Item>()

        if (data != null) {
            // Разбиваем строку и создаем элементы
            val itemsArray = data.split(";")
            for (itemString in itemsArray) {
                if (itemString.isNotEmpty()) {
                    val parts = itemString.split(",")
                    val id = parts[0].toInt()
                    val name = parts[1]
                    val title = parts[2]
                    val desc = parts[3]
                    val text = parts[4]
                    val price = parts[5].toInt()

                    loadedItems.add(Item(id, name, title, desc, text, price))
                }
            }
        }
        return loadedItems
    }

    companion object {
        private const val ADD_NOTE_REQUEST_CODE = 1
    }
}
