package com.example.gonzalezpauandroidstact18

    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.Button
    import android.widget.ImageView
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView

    class ProductAdapter(
        private val products: List<Product>,
        private var quantities: MutableMap<Int, Int>,
        private val onQuantityChanged: (Int, Int) -> Unit
    ) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

        class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.productImage)
            val nameTextView: TextView = itemView.findViewById(R.id.productName)
            val priceTextView: TextView = itemView.findViewById(R.id.productPrice)
            val quantityTextView: TextView = itemView.findViewById(R.id.quantityText)
            val addButton: Button = itemView.findViewById(R.id.addButton)
            val removeButton: Button = itemView.findViewById(R.id.removeButton)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product, parent, false)
            return ProductViewHolder(view)
        }

        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
            val product = products[position]
            holder.imageView.setImageResource(product.imageResId)
            holder.nameTextView.text = product.name
            holder.priceTextView.text = String.format("%.2f€", product.price)

            val currentQuantity = quantities[product.id] ?: 0
            holder.quantityTextView.text = currentQuantity.toString()

            holder.addButton.setOnClickListener {
                val newQuantity = currentQuantity + 1
                quantities[product.id] = newQuantity
                holder.quantityTextView.text = newQuantity.toString()
                onQuantityChanged(product.id, newQuantity)
            }

            holder.removeButton.setOnClickListener {
                if (currentQuantity > 0) {
                    val newQuantity = currentQuantity - 1
                    quantities[product.id] = newQuantity
                    holder.quantityTextView.text = newQuantity.toString()
                    onQuantityChanged(product.id, newQuantity)
                }
            }
        }

        override fun getItemCount() = products.size
    }
