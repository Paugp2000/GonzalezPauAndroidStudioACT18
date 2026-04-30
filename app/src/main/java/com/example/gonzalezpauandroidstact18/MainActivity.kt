package com.example.gonzalezpauandroidstact18

// MainActivity.kt
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var totalPriceTextView: TextView
    private lateinit var products: List<Product>
    private val quantities = HashMap<Int, Int>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initProducts()
        setupRecyclerView()
        setupButtons()
    }

    private fun initProducts() {

        products = listOf(
            Product(1, "Pomes", 1.20, R.drawable.manzana),
            Product(2, "Plàtans", 0.80, R.drawable.banana),
            Product(3, "Taronges", 1.50, R.drawable.naranja),
            Product(4, "Raïm", 3.20, R.drawable.uvas)
        )
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.productsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = ProductAdapter(products, quantities) { _, newQuantity ->
            updateTotalPrice()
        }
        recyclerView.adapter = adapter
    }

    private fun setupButtons() {
        totalPriceTextView = findViewById(R.id.totalPriceTextView)
        val summaryButton = findViewById<Button>(R.id.summaryButton)


        summaryButton.setOnClickListener {
            val intent = Intent(this, SummaryActivity::class.java)
            intent.putExtra("product_ids", ArrayList(quantities.keys))
            intent.putExtra("quantities", HashMap(quantities))
            startActivity(intent)
        }

        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        var total = 0.0
        products.forEach { product ->
            val quantity = quantities[product.id] ?: 0
            total += product.price * quantity
        }
        totalPriceTextView.text = String.format("Total: %.2f€", total)
    }
}