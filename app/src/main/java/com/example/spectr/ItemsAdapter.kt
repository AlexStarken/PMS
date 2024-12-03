package com.example.spectr

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(var items: MutableList<Item>, var context: Context) : RecyclerView.Adapter<ItemsAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.item_list_image)
        val title: TextView = view.findViewById(R.id.item_list_title)
        val desc: TextView = view.findViewById(R.id.item_list_desc)
        val price: TextView = view.findViewById(R.id.item_list_price)
        val bollocks: Button = view.findViewById(R.id.item_list_button)
        val deleteButton: Button = view.findViewById(R.id.item_list_delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = items[position].title
        holder.desc.text = items[position].text
        holder.price.text = items[position].price.toString()

        val imageId = context.resources.getIdentifier(
            items[position].image, "drawable", context.packageName
        )
        holder.image.setImageResource(imageId)

        holder.bollocks.setOnClickListener {
            val intent = Intent(context, Fragment::class.java)
            intent.putExtra("itemTitle", items[position].title)
            intent.putExtra("itemText", items[position].desc)
            context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            removeItem(position)
        }
    }

    // Метод для удаления элемента
    private fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)

        // Сохранение обновленного списка в SharedPreferences
        (context as ItemsActivity).saveItemsToPreferences()
    }
}