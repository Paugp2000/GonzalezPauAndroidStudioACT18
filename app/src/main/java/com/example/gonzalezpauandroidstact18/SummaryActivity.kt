package com.example.gonzalezpauandroidstact18

// SummaryActivity.kt
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SummaryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var totalSummaryTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        setupRecyclerView()
        setupButtons()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.summaryRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val quantities = intent.getSerializableExtra("quantities") as? ArrayList<*> ?: return
        val products = intent.getSerializableExtra("products") as? ArrayList<Product> ?: return

        val summaryItems = mutableListOf<Pair<Product, Int>>()
        quantities.forEach { entry ->
            val mapEntry = entry as? Map.Entry<*, *> ?: return@forEach
            val productId = mapEntry.key as Int
            val quantity = mapEntry.value as Int

            val product = products.find { it.id == productId }
            product?.let {
                summaryItems.add(Pair(it, quantity))
            }
        }

        val adapter = SummaryAdapter(summaryItems.filter { it.second > 0 })
        recyclerView.adapter = adapter

        totalSummaryTextView = findViewById(R.id.totalSummaryTextView)
        updateTotalSummary(summaryItems)


    }

    private fun setupButtons() {
        val backButton = findViewById<Button>(R.id.backButton)
        val confirmButton = findViewById<Button>(R.id.confirmButton)

        backButton.setOnClickListener { finish() }

        confirmButton.setOnClickListener {
            confirmPurchase()
        }
    }

    private fun updateTotalSummary(summaryItems: List<Pair<Product, Int>>) {
        var total = 0.0
        summaryItems.forEach { (product, quantity) ->
            total += product.price * quantity
        }
        totalSummaryTextView.text = String.format("Total resum: %.2f€", total)
    }

    private fun confirmPurchase() {
        val quantities = intent.getSerializableExtra("quantities") as? HashMap<Int, Int> ?: return
        val products = intent.getSerializableExtra("products") as? ArrayList<Product> ?: return

        Log.d("COMPRA", "=== CONFIRMACIÓ DE COMPRA ===")
        quantities.forEach { entry ->
            val mapEntry = entry as? Map.Entry<*, *> ?: return@forEach
            val productId = mapEntry.key as Int
            val quantity = mapEntry.value as Int

            if (quantity > 0) {
                val product = products.find { it.id == productId }
                product?.let {
                    Log.d("COMPRA", "${it.name}: ${quantity} unitats x ${String.format("%.2f€", it.price)} = ${String.format("%.2f€", it.price * quantity)}")
                }
            }
        }
        Log.d("COMPRA", "============== FI =============")

        finish()
    }
}