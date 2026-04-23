package com.example.gonzalezpauandroidstact18

    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.ImageView
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView

    class SummaryAdapter(
        private val summaryItems: List<Pair<Product, Int>>
    ) : RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder>() {

        class SummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.summaryImage)
            val nameTextView: TextView = itemView.findViewById(R.id.summaryName)
            val quantityTextView: TextView = itemView.findViewById(R.id.summaryQuantity)
            val priceTextView: TextView = itemView.findViewById(R.id.summaryPrice)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_summary, parent, false)
            return SummaryViewHolder(view)
        }

        override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
            val (product, quantity) = summaryItems[position]
            holder.imageView.setImageResource(product.imageResId)
            holder.nameTextView.text = product.name
            holder.quantityTextView.text = "Quantitat: $quantity"
            holder.priceTextView.text = String.format("Preu total: %.2f€", product.price * quantity)
        }

        override fun getItemCount() = summaryItems.size
    }
