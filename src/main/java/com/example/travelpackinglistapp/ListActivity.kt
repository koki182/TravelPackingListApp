package com.example.travelpackinglistapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnDisplayAll = findViewById<Button>(R.id.btnDisplayAll)
        val btnDisplayTwoPlus = findViewById<Button>(R.id.btnDisplayTwoPlus)
        val btnReturn = findViewById<Button>(R.id.btnReturn)
        val textViewList = findViewById<TextView>(R.id.textViewList)

        val items = intent.getStringArrayListExtra("items") ?: arrayListOf()
        val categories = intent.getStringArrayListExtra("categories") ?: arrayListOf()
        val quantities = intent.getIntegerArrayListExtra("quantities") ?: arrayListOf()
        val comments = intent.getStringArrayListExtra("comments") ?: arrayListOf()

        // Show all items
        btnDisplayAll.setOnClickListener {
            val builder = StringBuilder()
            if (items.isEmpty()) {
                builder.append("List is empty")
            } else {
                for (i in items.indices) {
                    val cat = if (i < categories.size) categories[i] else ""
                    val qty = if (i < quantities.size) quantities[i] else 0
                    val com = if (i < comments.size) comments[i] else ""
                    builder.append("${items[i]} - $cat - $qty - $com\n")
                }
            }
            textViewList.text = builder.toString()
        }

        // Show items with quantity >= 2
        btnDisplayTwoPlus.setOnClickListener {
            val builder = StringBuilder()
            var found = false
            for (i in items.indices) {
                val qty = if (i < quantities.size) quantities[i] else 0
                if (qty >= 2) {
                    builder.append("${items[i]} (x$qty)\n")
                    found = true
                }
            }
            if (!found) {
                textViewList.text = "No items with quantity 2 or more"
            } else {
                textViewList.text = builder.toString()
            }
        }

        // Return to main screen
        btnReturn.setOnClickListener {
            finish()
        }
    }
}
