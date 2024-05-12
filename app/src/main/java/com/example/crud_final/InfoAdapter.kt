package com.example.crud_final

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class InfoAdapter (private var info :List<Info>, context: Context) : RecyclerView.Adapter<InfoAdapter.InfoViewHolder>(){

    private val db: DatabaseHelper = DatabaseHelper(context)

    class InfoViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val update: ImageView = itemView.findViewById(R.id.UpdateButton)
        val delete: ImageView = itemView.findViewById(R.id.DeleteButton)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.info_item, parent, false)
        return InfoViewHolder(view)
    }

    override fun getItemCount(): Int = info.size

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        val info = info[position]
        holder.titleTextView.text = info.title
        holder.contentTextView.text = info.content

        holder.update.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply{
                putExtra("info_id", info.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.delete.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Delete Info")
                .setMessage("Are you sure you want to delete this info?")
                .setPositiveButton("Yes") { dialog, _ ->
                    db.deleteInfo(info.id)
                    refreshData(db.getInfo())
                    Toast.makeText(holder.itemView.context, "Info Deleted", Toast.LENGTH_SHORT)
                        .show()
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
    fun filter(query: String) {
        val filteredList = if (query.isNotEmpty()) {
            info.filter { it.title.contains(query, ignoreCase = true) || it.content.contains(query, ignoreCase = true) }
        } else {
            db.getInfo()
        }
        refreshData(filteredList)
    }

    fun refreshData(newInfo: List<Info>){
        info = newInfo
        notifyDataSetChanged()
    }

}