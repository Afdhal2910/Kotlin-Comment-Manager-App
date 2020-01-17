package com.example.commentmanagerapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.commentmanagerapp.R
import com.example.commentmanagerapp.model.Posts
import com.example.commentmanagerapp.view.DetailsPost

class DataAdapter (private var dataList: List<Posts>, private val context: Context) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_view, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: DataAdapter.ViewHolder, position: Int) {
        val dataModel=dataList.get(position)

        holder.titleTextView.text= dataModel.title
        holder.bodyTextView.text = dataModel.body

        holder.linearLayout.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailsPost::class.java)
            intent.putExtra("dd", dataModel.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView){
        lateinit var titleTextView: TextView
        lateinit var linearLayout: LinearLayout
        var bodyTextView: TextView

        init {
            titleTextView=itemLayoutView.findViewById(R.id.title)
            bodyTextView = itemLayoutView.findViewById(R.id.body)
            linearLayout = itemLayoutView.findViewById(R.id.llv)

        }
    }
}