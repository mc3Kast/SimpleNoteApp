package com.example.adodus.db

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.adodus.EditActivity
import com.example.adodus.R

class Adapter(listMain: ArrayList<ListItem>, context: Context) : RecyclerView.Adapter<Adapter.Holder>() {
    var listArray = listMain
    var conttextM = context

    class Holder(itemView: View, context : Context) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        var conttextM = context

        fun setData(item: ListItem){
            tvTitle.text = item.title

            itemView.setOnClickListener{

                val intent = Intent(conttextM, EditActivity::class.java).apply {

                    putExtra(IntentConstant.I_TITLE_KEY, item.title)
                    putExtra(IntentConstant.I_DESC_KEY, item.desc)

                }
                conttextM.startActivity(intent)

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(inflater.inflate(R.layout.rc_item, parent, false), conttextM)
    }

    override fun getItemCount(): Int {
        return listArray.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setData(listArray.get(position))
    }

    fun updateAdapter(listItems: List<ListItem>){
        listArray.clear()
        listArray.addAll(listItems)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int, dbManager: DbManager){
        dbManager.removeFrom(listArray[position].id.toString())
        listArray.removeAt(position)
        notifyItemRangeChanged(0,listArray.size)
        notifyItemRemoved(position)
    }
}