package com.example.travelpackinglistapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    // Parallel arrays
    private val items = mutableListOf<String>()
    private val categories = mutableListOf<String>()
    private val quantities = mutableListOf<Int>()
    private val comments = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnGoList = findViewById<Button>(R.id.btnGoList)
        val btnExit = findViewById<Button>(R.id.btnExit)

        val editItem = findViewById<EditText>(R.id.editItem)
        val editCategory = findViewById<EditText>(R.id.editCategory)
        val editQuantity = findViewById<EditText>(R.id.editQuantity)
        val editComment = findViewById<EditText>(R.id.editComment)

        // Add to packing list
        btnAdd.setOnClickListener {
            try {
                val itemName = editItem.text.toString()
                val category = editCategory.text.toString()
                val quantityStr = editQuantity.text.toString()
                val comment = editComment.text.toString()

                if (itemName.isBlank() || category.isBlank() || quantityStr.isBlank()) {
                    Toast.makeText(this, "Item, category and quantity required", Toast.LENGTH_SHORT).show()
                } else {
                    val quantity = quantityStr.toInt()
                    items.add(itemName)
                    categories.add(category)
                    quantities.add(quantity)
                    comments.add(comment)
                    Log.d("PackingApp", "Added: $itemName ($quantity)")
                    Toast.makeText(this, "Item added!", Toast.LENGTH_SHORT).show()
                    
                    // Clear inputs
                    editItem.text.clear()
                    editCategory.text.clear()
                    editQuantity.text.clear()
                    editComment.text.clear()
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Quantity must be a number", Toast.LENGTH_SHORT).show()
            }
        }

        // Navigate to second screen
        btnGoList.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            // Pass arrays to second screen
            intent.putStringArrayListExtra("items", ArrayList(items))
            intent.putStringArrayListExtra("categories", ArrayList(categories))
            intent.putIntegerArrayListExtra("quantities", ArrayList(quantities))
            intent.putStringArrayListExtra("comments", ArrayList(comments))
            startActivity(intent)
        }

        // Exit app
        btnExit.setOnClickListener {
            finish()
        }
    }
}
