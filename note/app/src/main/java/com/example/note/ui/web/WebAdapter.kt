package com.example.note.ui.web

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.note.R
import com.example.note.network.OnlineProperty

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val id: TextView = itemView.findViewById(R.id.web_id)
    val userId: TextView= itemView.findViewById(R.id.web_user_id)
    val title: TextView = itemView.findViewById(R.id.web_title)
    val body: TextView = itemView.findViewById(R.id.web_body)

}

class WebAdapter: RecyclerView.Adapter<ViewHolder>() {

    var data = listOf<OnlineProperty>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources

        holder.id.text = item.id
        holder.userId.text = item.userId
        holder.title.text = item.title
        holder.body.text = item.body


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.web_item, parent, false)

        return ViewHolder(view)
    }
}