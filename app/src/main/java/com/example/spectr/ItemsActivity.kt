package com.example.spectr

import OnSwipeTouchListener
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_items)

        val itemsList: RecyclerView = findViewById(R.id.itemsList)
        var button: Button = findViewById(R.id.exit_button)
        val items = arrayListOf<Item>()

        button.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        items.add(Item(1, "book", "100reps", "about sport", "good one", 100))
        items.add(Item(2, "film", "roadHouse", "watched a day ago", "Crazy McGregor,love him", 200))
        items.add(Item(3, "video", "GregPlitt", "a legend", "R.I.P.", 300))

        itemsList.layoutManager = LinearLayoutManager(this)
        itemsList.adapter = ItemsAdapter(items, this)


        val ctx = this
        window.decorView.setOnTouchListener(object : OnSwipeTouchListener(ctx) {

            override fun onSwipeTop() {
                super.onSwipeTop()
            }

            override fun onSwipeBottom() {
                super.onSwipeBottom()
            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()
            }

            override fun onSwipeRight() {
                val intent = Intent(ctx, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

    }
}