package com.example.commentmanagerapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.commentmanagerapp.R
import com.example.commentmanagerapp.model.Comment
import kotlin.collections.ArrayList

class NewCommentAdapter(val arrayList: ArrayList<Comment>,
                      var mainfilteredList : ArrayList<Comment>)
    : RecyclerView.Adapter<NewCommentAdapter.ViewHolder>(), Filterable {

    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewCommentAdapter.ViewHolder {
        context = parent.context
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: NewCommentAdapter.ViewHolder, position: Int) {
        holder.bindItems(mainfilteredList[position])
        val model : Comment = mainfilteredList.get(position)

        holder.row_name.text = model.name
        holder.row_email.text = model.email
        holder.row_body.text = model.body

    }

    override fun getItemCount(): Int {
        return mainfilteredList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var row_name : TextView
        lateinit var row_body : TextView
        lateinit var row_email : TextView


        fun bindItems(user: Comment) {
            row_body = itemView.findViewById(R.id.row_body) as TextView
            row_name =  itemView.findViewById(R.id.row_name) as TextView
            row_email = itemView.findViewById(R.id.row_email) as TextView
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    mainfilteredList = arrayList
                } else {
                    val filteredList = java.util.ArrayList<Comment>()
                    for (row in arrayList) {

                        if ((row.name!!.toLowerCase().contains(charString)) || (row.email!!.toLowerCase().contains(charString)) ||
                             row.body!!.toLowerCase().contains(charString))  {
                            filteredList.add(row)
                        }
                    }
                    if (filteredList.size > 0) {
                        mainfilteredList = filteredList
                    } else {
                        mainfilteredList = arrayList
                        Toast.makeText(context, "No Match", Toast.LENGTH_SHORT).show()
                    }
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = mainfilteredList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                mainfilteredList = filterResults.values as java.util.ArrayList<Comment>
                notifyDataSetChanged()
            }
        }
    }
}